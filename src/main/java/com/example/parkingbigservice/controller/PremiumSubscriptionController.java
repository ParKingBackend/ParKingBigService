package com.example.parkingbigservice.controller;

import com.example.parkingbigservice.model.Client;
import com.example.parkingbigservice.model.PremiumSubscription;
import com.example.parkingbigservice.repository.ClientRepository;
import com.example.parkingbigservice.service.ClientService;
import com.example.parkingbigservice.service.PersonService;
import com.example.parkingbigservice.service.PremiumSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscription")
public class PremiumSubscriptionController {


    private final PremiumSubscriptionService premiumSubscriptionService;
    private final ClientRepository clientRepository;

    private final PersonService personService;
    private final  ClientService clientService;


    @Autowired
    public PremiumSubscriptionController(PersonService personService, ClientService clientService, ClientRepository clientRepository, PremiumSubscriptionService premiumSubscriptionService) {
        this.premiumSubscriptionService = premiumSubscriptionService;
        this.personService = personService;
        this.clientService = clientService;
        this.clientRepository = clientRepository;
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


    //make a edit subscription method
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePremiumSubscription(@PathVariable Long id, @RequestBody PremiumSubscription updatedPremiumSubscription) {
        PremiumSubscription updated = premiumSubscriptionService.updatePremiumSubscription(id, updatedPremiumSubscription);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Subscription with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/get/all")
    public List<PremiumSubscription> getAllPremiumSubscriptions() {
        return premiumSubscriptionService.getAllSubscriptions();
    }

}
