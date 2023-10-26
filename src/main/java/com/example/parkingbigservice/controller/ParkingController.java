package com.example.parkingbigservice.controller;

import com.example.parkingbigservice.model.Parking;
import com.example.parkingbigservice.model.Report;
import com.example.parkingbigservice.model.Reservations;
import com.example.parkingbigservice.model.Review;
import com.example.parkingbigservice.service.ParkingService;
import com.example.parkingbigservice.service.ReportService;
import com.example.parkingbigservice.service.ReservationsService;
import com.example.parkingbigservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final Logger logger = Logger.getLogger(ParkingController.class.getName());
    private final ParkingService parkingService;
    private final ReportService reportService;
    private final ReviewService reviewService;
    private final ReservationsService reservationsService;

    @Autowired
    public ParkingController(ParkingService parkingService, ReportService reportService, ReviewService reviewService, ReservationsService reservationsService) {
        this.parkingService = parkingService;
        this.reportService = reportService;
        this.reservationsService = reservationsService;
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
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
        Parking parking = parkingService.findById(id);

        if (parking == null) {
            return ResponseEntity.badRequest().body("Parking not found");
        }
        try {
            List<Review> reviews = reviewService.findByParking(parking);
            for (Review review : reviews) {
                reviewService.deleteReview(review.getId());
            }
            List<Reservations> reservations = reservationsService.findByParking(parking);
            for (Reservations reservation : reservations) {
                reservationsService.deleteReservations(reservation.getId());
            }
            List<Report> reports = reportService.findByParking(parking);
            for (Report report : reports) {
                reportService.deleteReport(report.getId());
            }
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
            if (updatedParking.getAddress() != null) {
                parking.setAddress(updatedParking.getAddress());
            }
            if (updatedParking.getPrice() != null) {
                parking.setPrice(updatedParking.getPrice());
            }
            if (updatedParking.getPartnerId() != null) {
                parking.setPartnerId(updatedParking.getPartnerId());
            }
            if (updatedParking.getMaxSpotsCount() != null) {
                parking.setMaxSpotsCount(updatedParking.getMaxSpotsCount());
            }
            if (updatedParking.getSpotsTaken() != null) {
                parking.setSpotsTaken(updatedParking.getSpotsTaken());
            }
            if (updatedParking.getIsPremium() != null) {
                parking.setIsPremium(updatedParking.getIsPremium());
            }
            if (updatedParking.getIsDisabled() != null) {
                parking.setIsDisabled(updatedParking.getIsDisabled());
            }
            parkingService.updateParking(parking);
            return ResponseEntity.ok(parking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}
