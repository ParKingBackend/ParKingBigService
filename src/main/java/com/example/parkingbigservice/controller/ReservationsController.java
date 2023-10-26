package com.example.parkingbigservice.controller;

import com.example.parkingbigservice.model.*;
import com.example.parkingbigservice.service.ClientService;
import com.example.parkingbigservice.service.ParkingService;
import com.example.parkingbigservice.service.ReservationsService;
import com.example.parkingbigservice.service.request.ReservationCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/reservations")
public class ReservationsController {
    private static final Logger logger = Logger.getLogger(ReservationsController.class.getName());
    private final ClientService clientService;
    private final ParkingService parkingService;
    private final ReservationsService reservationsService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    public ReservationsController(ReservationsService reservationsService, ParkingService parkingService, ClientService clientService) {
        this.reservationsService = reservationsService;
        this.parkingService = parkingService;
        this.clientService = clientService;
    }
    @PostMapping("/create/{parkingId}")
    public ResponseEntity<Object> createReservations(@PathVariable Long parkingId, @RequestBody ReservationCreateRequest request) {
        Optional<Parking> optionalParking = Optional.ofNullable(parkingService.findById(parkingId));

        if (optionalParking.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Long clientId = request.getClientId();
        if (clientId == null) {
            return ResponseEntity.badRequest().body("clientId is required in the request.");
        }

        Optional<Client> optionalClient = Optional.ofNullable(clientService.findById(clientId));

        if (optionalClient.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Client with id " + request.getClientId() + " not found.");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Parking parking = optionalParking.get();
        Client client = optionalClient.get();

        Reservations reservations = new Reservations(parking, client, request.getEndTime());
        Reservations createdReservations = reservationsService.createReservations(reservations);
        sendReviewToWebhook(createdReservations);

        return ResponseEntity.ok(createdReservations);
    }
    private void sendReviewToWebhook(Reservations reservations) {
        try {
            String webhookURL = "https://parkingpartner.duckdns.org/api/webhook.php";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-KEY", "123api321");

            HttpEntity<Reservations> entity = new HttpEntity<>(reservations, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(webhookURL, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Reservations data sent to webhook successfully");
            } else {
                logger.warning("Failed to send reservations data to the webhook. HTTP Status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            logger.severe("Exception while sending review data to the webhook: " + e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Reservations> getReservationsById(@PathVariable Long id) {
        try {
            Reservations reservations = reservationsService.findById(id);

            if (reservations == null) {
                return ResponseEntity.badRequest().body(null);
            }

            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/get/all")
    public List<Reservations> getAllClients() {
        return reservationsService.getAllClients();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReservations(@PathVariable Long id) {
        try {
            boolean deleted = reservationsService.deleteReservations(id);

            if (deleted) {
                return ResponseEntity.ok("Reservation deleted successfully");
            } else {
                return ResponseEntity.badRequest().body("Reservation not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Reservation deletion failed");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Reservations> updateReservations(@PathVariable Long id, @RequestBody Reservations updatedReservations) {
        try {
            Reservations reservations = reservationsService.findById(id);

            if(updatedReservations.getEndTime() != null) {
                reservations.setEndTime(updatedReservations.getEndTime());
            }

            reservationsService.updateReservations(reservations);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
