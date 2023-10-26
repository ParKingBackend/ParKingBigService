package com.example.parkingbigservice.controller;


import com.example.parkingbigservice.model.Partner;
import com.example.parkingbigservice.repository.PartnerRepository;
import com.example.parkingbigservice.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private final PartnerService partnerService;

    private final PartnerRepository partnerRepository;
    @Autowired
    public PartnerController(PartnerService partnerService, PartnerRepository partnerRepository) {
        this.partnerService = partnerService;
        this.partnerRepository = partnerRepository;
    }

    @GetMapping("/get/all")
    public List<Partner> getAllPartners() {
        return partnerService.getAllPartners();
    }

    @GetMapping("/get/{id}")
    public Partner getPartnerById(@PathVariable Long id) {
        return partnerService.getPartnerById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Partner> createPartner(@RequestBody Partner partner) {
        Partner createdPartner = partnerService.createPartner(partner);
        return new ResponseEntity<>(createdPartner, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePartner(@PathVariable Long id) {
        Partner partner = partnerService.getPartnerById(id);

        if (partner == null) {
            return ResponseEntity.badRequest().body("Partner not found");
        }

        partnerService.deletePartner(id);

        return ResponseEntity.ok("Partner deleted");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePartner(@PathVariable Long id, @RequestBody Partner partner) {
        Partner partnerToUpdate = partnerService.getPartnerById(id);

        if (partnerToUpdate == null) {
            return ResponseEntity.badRequest().body("Partner not found");
        }

        partnerToUpdate.setName(partner.getName());
        partnerToUpdate.setBankAccount(partner.getBankAccount());

        partnerService.updatePartner(partnerToUpdate);

        return ResponseEntity.ok("Partner updated");
    }


}
