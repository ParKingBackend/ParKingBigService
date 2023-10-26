package com.example.parkingbigservice.controller;

import com.example.parkingbigservice.model.Client;
import com.example.parkingbigservice.service.ClientService;
import com.example.parkingbigservice.service.request.ClientRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody ClientRegistrationRequest registrationRequest) {
        try {
            String username = registrationRequest.getUsername();
            String password = registrationRequest.getPassword();
            String email = registrationRequest.getEmail();

            // Check if the username is already taken
            if (clientService.findByUsername(username) != null) {
                return ResponseEntity.badRequest().body("Username is already taken");
            }

            clientService.registerClient(username, password, email);

            return ResponseEntity.ok("Client registration successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Client registration failed");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            Client client = clientService.findByUsername(username);

            if (client == null) {
                return ResponseEntity.badRequest().body("Client not found");
            }

            String hashedPasswordFromDatabase = client.getPassword();

            if (passwordEncoder.matches(password, hashedPasswordFromDatabase)) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.badRequest().body("Login failed: Incorrect password");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login failed");
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        try {
            Client client = clientService.findById(id);

            if (client == null) {
                return ResponseEntity.badRequest().body(null);
            }

            return ResponseEntity.ok(client);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        try {
            boolean deleted = clientService.deleteClient(id);

            if (deleted) {
                return ResponseEntity.ok("Client deleted successfully");
            } else {
                return ResponseEntity.badRequest().body("Client not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Client deletion failed");
        }
    }

    @GetMapping("/get/all")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @PutMapping("/{clientId}/update-bank-account")
    public ResponseEntity<String> addBankAccountToClient(
            @PathVariable Long clientId,
            @RequestBody Map<String, String> requestBody) {
        String bankAccount = requestBody.get("bankAccount");
        clientService.addBankAccount(clientId, bankAccount);
        return ResponseEntity.ok("Bank account added successfully.");
    }

    @PutMapping("/{clientId}/update-image")
    public ResponseEntity<String> addImageToClient(
            @PathVariable Long clientId,
            @RequestBody Map<String, String> requestBody) {
        String image = requestBody.get("image");
        clientService.addImage(clientId, image);
        return ResponseEntity.ok("Image added successfully.");
    }

    @PutMapping("/{clientId}/update-xp")
    public ResponseEntity<String> updateXp(
            @PathVariable Long clientId,
            @RequestBody Map<String, Integer> requestBody) {
        int xp = requestBody.get("xp");
        clientService.updateXp(clientId, xp);
        return ResponseEntity.ok("XP updated successfully.");
    }

    @PutMapping("/{clientId}/update-level")
    public ResponseEntity<String> updateLevel(
            @PathVariable Long clientId,
            @RequestBody Map<String, Integer> requestBody) {
        int level = requestBody.get("level");
        clientService.updateLevel(clientId, level);
        return ResponseEntity.ok("Level updated successfully.");
    }

    @PutMapping("/{clientId}/update-username")
    public ResponseEntity<String> updateUsername(
            @PathVariable Long clientId,
            @RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        clientService.updateUsername(clientId, username);
        return ResponseEntity.ok("Username updated successfully.");
    }

    @PutMapping("/update/{clientId}")
    public ResponseEntity<Client> updateClient(@PathVariable Long clientId, @RequestBody Client updatedClient) {
        try {
            Client client = clientService.findById(clientId);
            if (updatedClient.getUsername() != null) {
                client.setUsername(updatedClient.getUsername());
            }
            if (updatedClient.getPassword() != null) {
                client.setPassword(updatedClient.getPassword());
            }
            if (updatedClient.getEmail() != null) {
                client.setEmail(updatedClient.getEmail());
            }
            if (updatedClient.getBankAccount() != null) {
                client.setBankAccount(updatedClient.getBankAccount());
            }
            if (updatedClient.getImage() != null) {
                client.setImage(updatedClient.getImage());
            }
            if (updatedClient.getXp() != null) {
                client.setXp(updatedClient.getXp());
            }
            if (updatedClient.getLevel() != null) {
                client.setLevel(updatedClient.getLevel());
            }
            clientService.updateClient(client);
            return ResponseEntity.ok(client);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
