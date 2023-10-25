package com.example.parkingbigservice.model;

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
    private BigDecimal discountAmount;
    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // Constructors, getters, and setters

    public PremiumSubscription() {
        // Default constructor
    }

    public PremiumSubscription(LocalDate endDate, BigDecimal discountAmount) {
        this.endDate = endDate;
        this.discountAmount = discountAmount;
    }

    // Getters and setters

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
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
