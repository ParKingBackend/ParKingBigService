package com.example.parkingbigservice.repository;

import com.example.parkingbigservice.model.PremiumSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumSubscriptionRepository extends JpaRepository<PremiumSubscription, Long> {

}
