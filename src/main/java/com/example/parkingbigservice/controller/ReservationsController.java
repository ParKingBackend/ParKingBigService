package com.example.parkingbigservice.controller;

import com.example.parkingbigservice.model.Client;
import com.example.parkingbigservice.model.Parking;
import com.example.parkingbigservice.model.Reservations;
import com.example.parkingbigservice.service.ClientService;
import com.example.parkingbigservice.service.ParkingService;
import com.example.parkingbigservice.service.ReservationsService;
import com.example.parkingbigservice.service.request.ReservationCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationsController {
    private final ClientService clientService;
    private final ParkingService parkingService;
    private final ReservationsService reservationsService;
    @Autowired
    public ReservationsController(ReservationsService reservationsService, ParkingService parkingService, ClientService clientService) {
        this.reservationsService = reservationsService;
        this.parkingService = parkingService;
        this.clientService = clientService;
    }
    @PostMapping("/add/{parkingId}")
    public ResponseEntity<Object> createReservations(@PathVariable Long parkingId, @RequestBody ReservationCreateRequest request) {
        Optional<Parking> optionalParking = Optional.ofNullable(parkingService.findById(parkingId));
        if (optionalParking.isPresent()) {
            Parking parking = optionalParking.get();
            if (request.getClientId() != null) {
                Optional<Client> optionalClient = Optional.ofNullable(clientService.findById(request.getClientId()));
                if (optionalClient.isPresent()) {
                    Client client = optionalClient.get();
                    Reservations reservations = new Reservations(parking, client, request.getEndTime());
                    Reservations createdReservations = reservationsService.createReservations(reservations);
                    return ResponseEntity.ok(createdReservations);
                } else {
                    Map<String, String> response = new HashMap<>();
                    response.put("error", "Client with id " + request.getClientId() + " not found.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where client_id is not provided in the request
                Map<String, String> response = new HashMap<>();
                response.put("error", "clientId is required in the request.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Parking with id " + parkingId + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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
}
