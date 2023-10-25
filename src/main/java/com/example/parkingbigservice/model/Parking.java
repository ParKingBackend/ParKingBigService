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
    @Column(name = "is_Premium", columnDefinition = "TINYINT(1)") // Specify TINYINT(1)
    private boolean isPremium;
    private Long partnerId;
    private int maxSpotsCount;
    private int spotsTaken;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Column(name = "is_Disabled", columnDefinition = "TINYINT(1)") // Specify TINYINT(1)
    private boolean isDisabled;

    public Parking() {
    }

    public Parking(String address, BigDecimal price, boolean isPremium, Long partnerId, int maxSpotsCount, int spotsTaken, LocalDateTime startTime, LocalDateTime endTime, boolean isDisabled) {
        this.address = address;
        this.price = price;
        this.isPremium = isPremium;
        this.partnerId = partnerId;
        this.maxSpotsCount = maxSpotsCount;
        this.spotsTaken = spotsTaken;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDisabled = isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }


    public boolean getIsPremium() {
        return isPremium;
    }
    @Override
    public String toString() {
        return "Parking{" +
                "address='" + address + '\'' +
                ", price=" + price +
                ", isPremium=" + isPremium +
                ", partnerId=" + partnerId +
                ", maxSpotsCount=" + maxSpotsCount +
                ", spotsTaken=" + spotsTaken +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isDisabled=" + isDisabled +
                '}';
    }
}