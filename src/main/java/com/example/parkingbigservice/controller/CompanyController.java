package com.example.parkingbigservice.controller;

import com.example.parkingbigservice.model.Client;
import com.example.parkingbigservice.model.Company;
import com.example.parkingbigservice.repository.ClientRepository;
import com.example.parkingbigservice.service.ClientService;
import com.example.parkingbigservice.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/companies")
public class CompanyController {


    private final CompanyService companyService;

    private final ClientService clientService;

    private final ClientRepository clientRepository;

    @Autowired
    public CompanyController(CompanyService companyService, ClientService clientService, ClientRepository clientRepository) {
        this.companyService = companyService;
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/get/all")
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public Optional<Company> getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @PostMapping("/create/{clientId}")
    public ResponseEntity<Object> createCompany(@PathVariable Long clientId, @RequestBody Company company) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            company.setClient(client);

            Company createdCompany = companyService.createCompany(company);

            return ResponseEntity.ok(createdCompany);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Client with ID " + clientId + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/edit/{id}")
    public Optional<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        return companyService.updateCompany(id, company);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }




}
