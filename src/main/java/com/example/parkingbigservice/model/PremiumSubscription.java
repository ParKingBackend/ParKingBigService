package com.example.parkingbigservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "premium_subscriptions")
public class PremiumSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key generated automatically
    private LocalDate endDate;
    private Double discountAmount;
    @OneToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    // Constructors, getters, and setters

    public PremiumSubscription() {
        // Default constructor
    }

    public PremiumSubscription(LocalDate endDate, Double discountAmount) {
        this.endDate = endDate;
        this.discountAmount = discountAmount;
    }

    // Getters and setters

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "PremiumSubscription{" +
                "id=" + id +
                ", endDate=" + endDate +
                ", discountAmount=" + discountAmount +
                '}';
    }
}
