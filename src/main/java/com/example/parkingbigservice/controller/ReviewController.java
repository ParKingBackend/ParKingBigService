package com.example.parkingbigservice.controller;


import com.example.parkingbigservice.model.Client;
import com.example.parkingbigservice.model.Parking;
import com.example.parkingbigservice.model.Review;
import com.example.parkingbigservice.service.ClientService;
import com.example.parkingbigservice.service.ParkingService;
import com.example.parkingbigservice.service.ReviewService;
import com.example.parkingbigservice.service.request.ReviewCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private static final Logger logger = Logger.getLogger(ReviewController.class.getName());

    private final ReviewService reviewService;
    private final ClientService clientService;

    private final ParkingService parkingService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    public ReviewController(ReviewService reviewService, ParkingService parkingService, ClientService clientService) {
        this.reviewService = reviewService;
        this.parkingService = parkingService;
        this.clientService = clientService;
    }
    @PostMapping("/create/{parkingId}")
    public ResponseEntity<Object> createReview(@PathVariable Long parkingId, @RequestBody ReviewCreateRequest request) {
        Optional<Parking> optionalParking = Optional.ofNullable(parkingService.findById(parkingId));

        if (optionalParking.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (request.getClientId() == null) {
            return ResponseEntity.badRequest().body("Rating, description, title, and clientId are required in the request.");
        }

        Optional<Client> optionalClient = Optional.ofNullable(clientService.findById(request.getClientId()));

        if (optionalClient.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Client with id " + request.getClientId() + " not found.");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Parking parking = optionalParking.get();
        Client client = optionalClient.get();
        Review review = new Review(request.getTitle(), request.getDescription(), request.getRating(), client, parking);
        review.setPostedTime(LocalDateTime.now());
        Review createdReview = reviewService.createReview(review);
        sendReviewToWebhook(createdReview);
        return ResponseEntity.ok(createdReview);
    }
    private void sendReviewToWebhook(Review review) {
        try {
            String webhookURL = "https://parkingpartner.duckdns.org/api/webhook.php";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-KEY", "123api321");

            HttpEntity<Review> entity = new HttpEntity<>(review, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(webhookURL, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Review data sent to webhook successfully");
            } else {
                logger.warning("Failed to send review data to the webhook. HTTP Status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            logger.severe("Exception while sending review data to the webhook: " + e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        try {
            Review Review = reviewService.findById(id);

            if (Review == null) {
                return ResponseEntity.badRequest().body(null);
            }

            return ResponseEntity.ok(Review);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/get/all")
    public List<Review> getAllClients() {
        return reviewService.getAllClients();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        try {
            boolean deleted = reviewService.deleteReview(id);

            if (deleted) {
                return ResponseEntity.ok("Review deleted successfully");
            } else {
                return ResponseEntity.badRequest().body("Review not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Review deletion failed");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review updatedReview) {
        try {
            Review review = reviewService.findById(id);

            if(updatedReview.getTitle() != null) {
                review.setTitle(updatedReview.getTitle());
            }
            if(updatedReview.getDescription() != null) {
                review.setDescription(updatedReview.getDescription());
            }
            if(updatedReview.getRating() != null) {
                review.setRating(updatedReview.getRating());
            }

            reviewService.updateReview(review);
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
