package com.example.parkingbigservice.model.dto;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class PremiumSubscriptionDTO {
    private Long id;
    private LocalDate endDate;
    private Double discountAmount;
    private Long clientId; // Include the client's ID

    // Constructors, getters, and setters for the fields

    public PremiumSubscriptionDTO() {
        // Default constructor
    }

    public PremiumSubscriptionDTO(Long id, LocalDate endDate, Double discountAmount, Long clientId) {
        this.id = id;
        this.endDate = endDate;
        this.discountAmount = discountAmount;
        this.clientId = clientId;
    }

    // Getters and setters for the fields

}
