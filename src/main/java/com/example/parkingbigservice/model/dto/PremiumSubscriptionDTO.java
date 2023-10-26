package com.example.parkingbigservice.model.dto;

import com.example.parkingbigservice.model.Client;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Client client; // Include the client's ID

    // Constructors, getters, and setters for the fields

    public PremiumSubscriptionDTO() {
        // Default constructor
    }

    public PremiumSubscriptionDTO(Long id, LocalDate endDate, Double discountAmount, Client client) {
        this.id = id;
        this.endDate = endDate;
        this.discountAmount = discountAmount;
        this.client = client;
    }

    // Getters and setters for the fields

    @JsonProperty("client")
    public Client getClient() {
        return client;
    }
}
