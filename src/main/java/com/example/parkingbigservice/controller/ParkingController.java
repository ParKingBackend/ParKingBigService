package com.example.parkingbigservice.controller;

import com.example.parkingbigservice.model.*;
import com.example.parkingbigservice.service.*;
import com.example.parkingbigservice.service.request.ParkingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private final PartnerService partnerService;

    @Autowired
    public ParkingController(ParkingService parkingService, PartnerService partnerService, ReportService reportService, ReviewService reviewService, ReservationsService reservationsService) {
        this.parkingService = parkingService;
        this.reportService = reportService;
        this.reservationsService = reservationsService;
        this.reviewService = reviewService;
        this.partnerService = partnerService;

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
            if (updatedParking.getPartner() != null) {
                parking.setPartner(updatedParking.getPartner());
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
            if (updatedParking.getStartTime() != null) {
                parking.setStartTime(updatedParking.getStartTime());
            }
            if (updatedParking.getEndTime() != null) {
                parking.setEndTime(updatedParking.getEndTime());
            }
            parkingService.updateParking(parking);
            return ResponseEntity.ok(parking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/create-with-partner")
    public ResponseEntity<String> createParkingSpot(@RequestBody ParkingRequest parkingRequest) {
        try {
            Parking parking = new Parking();
            parking.setAddress(parkingRequest.getAddress());
            parking.setIsDisabled(parkingRequest.getIsDisabled());
            parking.setIsPremium(parkingRequest.getIsPremium());
            parking.setMaxSpotsCount(parkingRequest.getMaxSpotsCount());
            parking.setPrice(parkingRequest.getPrice());
            parking.setSpotsTaken(parkingRequest.getSpotsTaken());
            Partner partner = partnerService.getPartnerById(parkingRequest.getPartnerId());
            if (partner == null) {
                return new ResponseEntity<>("Partner not found", HttpStatus.BAD_REQUEST);
            }
            parking.setPartner(partner);
            Parking savedParking = parkingService.createParking(parking);
            return new ResponseEntity<>("Parking spot created with ID: " + savedParking.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating parking spot: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}