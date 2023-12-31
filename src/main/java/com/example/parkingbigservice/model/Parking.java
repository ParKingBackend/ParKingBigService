package com.example.parkingbigservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "parking")
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private BigDecimal price;
    @Column(name = "is_Premium", columnDefinition = "TINYINT(1)")
    private Boolean isPremium;
    private Integer maxSpotsCount;
    private Integer spotsTaken;
    @Column(name = "is_Disabled", columnDefinition = "TINYINT(1)")
    private Boolean isDisabled;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // Define many-to-one relationship with Partner
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    public Parking() {
    }

    public Parking(String address, BigDecimal price, Boolean isPremium, Integer maxSpotsCount, Integer spotsTaken, Boolean isDisabled, LocalDateTime startTime, LocalDateTime endTime) {
        this.address = address;
        this.price = price;
        this.isPremium = isPremium;
        this.maxSpotsCount = maxSpotsCount;
        this.spotsTaken = spotsTaken;
        this.isDisabled = isDisabled;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and setters for other fields

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }

    @Override
    public String toString() {
        return "Parking{" + "address='" + address + '\'' + ", price=" + price + ", isPremium=" + isPremium + ", maxSpotsCount=" + maxSpotsCount + ", spotsTaken=" + spotsTaken + ", isDisabled=" + isDisabled + '}';
    }


}