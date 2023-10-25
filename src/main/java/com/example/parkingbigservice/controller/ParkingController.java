package com.example.parkingbigservice.controller;

import com.example.parkingbigservice.model.Parking;
import com.example.parkingbigservice.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @PutMapping("/edit/{id}")
    public Optional<Parking> updateParking(@PathVariable Long id, @RequestBody Parking parking) {
        logger.info("Updating Parking with ID: " + id);
        logger.info("Updated Parking values: " + parking);
        return parkingService.updateParking(id, parking);
    }
}
