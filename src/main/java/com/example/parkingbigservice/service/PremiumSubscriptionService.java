package com.example.parkingbigservice.service;

import com.example.parkingbigservice.model.PremiumSubscription;
import com.example.parkingbigservice.repository.PremiumSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PremiumSubscriptionService {

    private final PremiumSubscriptionRepository premiumSubscriptionRepository;


    @Autowired
    public PremiumSubscriptionService(PremiumSubscriptionRepository premiumSubscriptionRepository) {
        this.premiumSubscriptionRepository = premiumSubscriptionRepository;

    }

    public List<PremiumSubscription> getAllSubscriptions() {
        return premiumSubscriptionRepository.findAll();
    }

    public PremiumSubscription createPremiumSubscription(PremiumSubscription premiumSubscription) {
        return premiumSubscriptionRepository.save(premiumSubscription);
    }

    public Optional<PremiumSubscription> getPremiumSubscriptionById(Long id) {
        return premiumSubscriptionRepository.findById(id);
    }

    public boolean deletePremiumSubscription(Long id) {
        Optional<PremiumSubscription> optionalPremiumSubscription = premiumSubscriptionRepository.findById(id);

        if (optionalPremiumSubscription.isPresent()) {
            premiumSubscriptionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public PremiumSubscription findById(Long id) {
        return premiumSubscriptionRepository.findById(id).orElse(null);
    }

    public void updatePremiumSubscription(PremiumSubscription subscription) {
        premiumSubscriptionRepository.save(subscription);
    }
}
