package com.example.parkingbigservice.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reservations")
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;
    private Long clientId;
    @Column(name = "end_time")
    private LocalDateTime endTime;


    public Reservations() {
        // Default constructor
    }

    public Reservations(Parking parking, Long clientId, LocalDateTime endTime) {
        this.parking = parking;
        this.clientId = clientId;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", parking=" + parking +
                ", clientId=" + clientId +
                ", endDate=" + endTime +
                '}';
    }
}
