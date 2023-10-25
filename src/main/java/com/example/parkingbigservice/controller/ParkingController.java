package com.example.parkingbigservice.controller;

import com.example.parkingbigservice.model.Parking;
import com.example.parkingbigservice.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final Logger logger = Logger.getLogger(ParkingController.class.getName());
    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/add")
    public Parking createParking(@RequestBody Parking parking) {
        return parkingService.createParking(parking);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Parking> getParkingById(@PathVariable Long id) {
        try {
            Parking parking = parkingService.findById(id);

            if (parking == null) {
                return ResponseEntity.badRequest().body(null);
            }

            return ResponseEntity.ok(parking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/get/all")
    public List<Parking> getAllClients() {
        return parkingService.getAllClients();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteParking(@PathVariable Long id) {
        try {
            boolean deleted = parkingService.deleteParking(id);

            if (deleted) {
                return ResponseEntity.ok("Parking deleted successfully");
            } else {
                return ResponseEntity.badRequest().body("Parking not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Parking deletion failed");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Parking> updateParking(@PathVariable Long id, @RequestBody Parking updatedParking) {
        try {
            Parking parking = parkingService.findById(id);
            parking.setAddress(updatedParking.getAddress());
            parking.setPrice(updatedParking.getPrice());
            parking.setIsPremium(updatedParking.getIsPremium());
            parking.setPartnerId(updatedParking.getPartnerId());
            parking.setMaxSpotsCount(updatedParking.getMaxSpotsCount());
            parking.setSpotsTaken(updatedParking.getSpotsTaken());
            parking.setStartTime(updatedParking.getStartTime());
            parking.setEndTime(updatedParking.getEndTime());
            parking.setIsPremium(updatedParking.getIsPremium());
            parking.setIsDisabled(updatedParking.getIsDisabled());

            parkingService.updateParking(parking);

            // Log the successful update
            logger.info("Updated Parking with ID: " + id);

            return ResponseEntity.ok(parking);
        } catch (Exception e) {
            // Log any exceptions that occur during the update
            logger.severe("Error updating Parking with ID: " + id);
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }


}
