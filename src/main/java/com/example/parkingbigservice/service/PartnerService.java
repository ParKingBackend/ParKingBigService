package com.example.parkingbigservice.service;

import com.example.parkingbigservice.model.Partner;
import com.example.parkingbigservice.repository.PartnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerService {

    private final PartnerRepository PartnerRepository;

    public PartnerService(PartnerRepository PartnerRepository) {
        this.PartnerRepository = PartnerRepository;
    }

    public List<Partner> getAllPartners() {
        return PartnerRepository.findAll();
    }

    public Partner getPartnerById(Long id) {
        return PartnerRepository.findById(id).orElse(null);
    }


    public Partner createPartner(Partner partner) {
        return PartnerRepository.save(partner);
    }

    public void deletePartner(Long id) {
        PartnerRepository.deleteById(id);
    }

    public void updatePartner(Partner partnerToUpdate) {
        PartnerRepository.save(partnerToUpdate);
    }
}
