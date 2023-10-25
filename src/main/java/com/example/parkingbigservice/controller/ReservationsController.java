package com.example.parkingbigservice.controller;

import com.example.parkingbigservice.model.Parking;
import com.example.parkingbigservice.model.Reservations;
import com.example.parkingbigservice.service.ParkingService;
import com.example.parkingbigservice.service.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationsController {

    private final ParkingService parkingService;
    private final ReservationsService reservationsService;
    @Autowired
    public ReservationsController(ReservationsService reservationsService, ParkingService parkingService) {
        this.reservationsService = reservationsService;
        this.parkingService = parkingService;
    }
    @PostMapping("/add/{parkingId}")
    public ResponseEntity<Object> createReservations(@PathVariable Long parkingId, @RequestBody Reservations reservations) {
        Optional<Parking> optionalParking = Optional.ofNullable(parkingService.findById(parkingId));
        if (optionalParking.isPresent()) {
            Parking parking = optionalParking.get();
            reservations.setParking(parking);
            Reservations createdReservations = reservationsService.createReservations(reservations);
            return ResponseEntity.ok(createdReservations);
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

}
