package com.example.parkingbigservice.controller;


import com.example.parkingbigservice.model.Client;
import com.example.parkingbigservice.model.Parking;
import com.example.parkingbigservice.model.Review;
import com.example.parkingbigservice.service.ClientService;
import com.example.parkingbigservice.service.ParkingService;
import com.example.parkingbigservice.service.ReviewService;
import com.example.parkingbigservice.service.request.ReviewCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final ClientService clientService;
    private final ParkingService parkingService;
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
        return ResponseEntity.ok(createdReview);
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
