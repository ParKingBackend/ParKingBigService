package com.example.parkingbigservice.controller;

import com.example.parkingbigservice.model.Client;
import com.example.parkingbigservice.model.PremiumSubscription;
import com.example.parkingbigservice.model.dto.PremiumSubscriptionDTO;
import com.example.parkingbigservice.repository.ClientRepository;
import com.example.parkingbigservice.repository.PremiumSubscriptionRepository;
import com.example.parkingbigservice.service.ClientService;
import com.example.parkingbigservice.service.PremiumSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/subscription")
public class PremiumSubscriptionController {


    private final PremiumSubscriptionService premiumSubscriptionService;
    private final PremiumSubscriptionRepository premiumSubscriptionRepository;
    private final ClientRepository clientRepository;


    private final  ClientService clientService;


    @Autowired
    public PremiumSubscriptionController( ClientService clientService, ClientRepository clientRepository, PremiumSubscriptionService premiumSubscriptionService, PremiumSubscriptionRepository premiumSubscriptionRepository) {
        this.premiumSubscriptionService = premiumSubscriptionService;
        this.clientService = clientService;
        this.clientRepository = clientRepository;
        this.premiumSubscriptionRepository = premiumSubscriptionRepository;
    }

    @PostMapping("/create/{clientId}")
    public ResponseEntity<Object> createPremiumSubscription(@PathVariable Long clientId, @RequestBody PremiumSubscription premiumSubscription) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();

            // Check if a premium subscription already exists for the client
            if (client.getPremiumSubscription() != null) {
                // A premium subscription already exists
                Map<String, String> response = new HashMap<>();
                response.put("message", "Premium subscription already exists for the client.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            premiumSubscription.setClient(client);
            PremiumSubscription createdPremiumSubscription = premiumSubscriptionService.createPremiumSubscription(premiumSubscription);

            return ResponseEntity.ok(createdPremiumSubscription);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Client with ID " + clientId + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/update/premium-subscription/{id}")
    public ResponseEntity<PremiumSubscription> updatePremiumSubscription(@PathVariable Long id, @RequestBody PremiumSubscription updatedSubscription) {
        try {
            PremiumSubscription subscription = premiumSubscriptionService.findById(id);
            if (updatedSubscription.getEndDate() != null) {
                subscription.setEndDate(updatedSubscription.getEndDate());
            }
            if (updatedSubscription.getDiscountAmount() != null) {
                subscription.setDiscountAmount(updatedSubscription.getDiscountAmount());
            }
            if (updatedSubscription.getClient() != null) {
                subscription.setClient(updatedSubscription.getClient());
            }


            premiumSubscriptionService.updatePremiumSubscription(subscription);
            return ResponseEntity.ok(subscription);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getPremiumSubscriptionById(@PathVariable Long id) {
        Optional<PremiumSubscription> optionalPremiumSubscription = premiumSubscriptionService.getPremiumSubscriptionById(id);

        if (optionalPremiumSubscription.isPresent()) {
            PremiumSubscription premiumSubscription = optionalPremiumSubscription.get();
            return ResponseEntity.ok(premiumSubscription);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Subscription with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/get/all")
    public List<PremiumSubscriptionDTO> getAllPremiumSubscriptionsWithClients() {
        List<PremiumSubscription> premiumSubscriptions = premiumSubscriptionService.getAllSubscriptions();
        List<PremiumSubscriptionDTO> premiumSubscriptionsWithClients = new ArrayList<>();

        for (PremiumSubscription subscription : premiumSubscriptions) {
            PremiumSubscriptionDTO subscriptionDTO = new PremiumSubscriptionDTO(
                    subscription.getId(),
                    subscription.getEndDate(),
                    subscription.getDiscountAmount(),
                    subscription.getClient() // Include the full Client object
            );

            premiumSubscriptionsWithClients.add(subscriptionDTO);
        }

        return premiumSubscriptionsWithClients;
    }


    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePremiumSubscription(@PathVariable Long id) {
        boolean deleted = premiumSubscriptionService.deletePremiumSubscription(id);

        Map<String, String> response = new HashMap<>();
        if (deleted) {
            response.put("message", "Premium subscription with ID " + id + " has been deleted.");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Premium subscription with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }



}
