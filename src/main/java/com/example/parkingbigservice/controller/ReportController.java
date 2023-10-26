package com.example.parkingbigservice.controller;

import com.example.parkingbigservice.model.*;
import com.example.parkingbigservice.service.ClientService;
import com.example.parkingbigservice.service.ParkingService;
import com.example.parkingbigservice.service.ReportService;
import com.example.parkingbigservice.service.request.ReportCreateRequest;
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
@RequestMapping("/api/report")
public class ReportController {
    private static final Logger logger = Logger.getLogger(ReportController.class.getName());
    private final ParkingService parkingService;
    private final ReportService reportService;
    private final ClientService clientService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    public ReportController(ReportService reportService, ParkingService parkingService, ClientService clientService) {
        this.reportService = reportService;
        this.parkingService = parkingService;
        this.clientService = clientService;
    }
    @PostMapping("/create/{parkingId}")
    public ResponseEntity<Object> createReport(@PathVariable Long parkingId, @RequestBody ReportCreateRequest request) {
        Optional<Parking> optionalParking = Optional.ofNullable(parkingService.findById(parkingId));

        if (optionalParking.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Long clientId = request.getClientId();
        if (clientId == null) {
            return ResponseEntity.badRequest().body("clientId and description are required in the request.");
        }

        Optional<Client> optionalClient = Optional.ofNullable(clientService.findById(clientId));

        if (optionalClient.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Client with id " + clientId + " not found.");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Parking parking = optionalParking.get();
        Client client = optionalClient.get();

        Report report = new Report(request.getDescription(), client, parking);
        Report createdReport = reportService.createReport(report);
        sendReviewToWebhook(createdReport);
        return ResponseEntity.ok(createdReport);
    }
    private void sendReviewToWebhook(Report report) {
        try {
            String webhookURL = "https://parkingpartner.duckdns.org/api/webhook.php";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-KEY", "123api321");

            HttpEntity<Report> entity = new HttpEntity<>(report, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(webhookURL, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Report data sent to webhook successfully");
            } else {
                logger.warning("Failed to send report data to the webhook. HTTP Status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            logger.severe("Exception while sending review data to the webhook: " + e.getMessage());
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
    @GetMapping("/get/all")
    public List<Report> getAllClients() {
        return reportService.getAllClients();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable Long id) {
        try {
            boolean deleted = reportService.deleteReport(id);

            if (deleted) {
                return ResponseEntity.ok("Report deleted successfully");
            } else {
                return ResponseEntity.badRequest().body("Report not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Report deletion failed");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report updatedReport) {
        try {
            Report report = reportService.findById(id);

            if(updatedReport.getDescription() != null) {
                report.setDescription(updatedReport.getDescription());
            }

            reportService.updateReport(report);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
