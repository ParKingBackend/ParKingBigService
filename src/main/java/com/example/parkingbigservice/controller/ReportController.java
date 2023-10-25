package com.example.parkingbigservice.controller;

import com.example.parkingbigservice.model.Parking;
import com.example.parkingbigservice.model.Report;
import com.example.parkingbigservice.service.ParkingService;
import com.example.parkingbigservice.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ParkingService parkingService;
    private final ReportService reportService;
    @Autowired
    public ReportController(ReportService reportService, ParkingService parkingService) {
        this.reportService = reportService;
        this.parkingService = parkingService;
    }
    @PostMapping("/add/{parkingId}")
    public ResponseEntity<Object> createReport(@PathVariable Long parkingId, @RequestBody Report report) {
        Optional<Parking> optionalParking = Optional.ofNullable(parkingService.findById(parkingId));
        if (optionalParking.isPresent()) {
            Parking parking = optionalParking.get();
            report.setParking(parking);
            Report createdReport = reportService.createReport(report);
            return ResponseEntity.ok(createdReport);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Parking with id " + parkingId + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        try {
            Report report = reportService.findById(id);

            if (report == null) {
                return ResponseEntity.badRequest().body(null);
            }

            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
