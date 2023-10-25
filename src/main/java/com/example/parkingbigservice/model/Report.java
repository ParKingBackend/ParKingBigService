package com.example.parkingbigservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Setter
@Getter
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;
    private String description;
    @Column(name = "client_id")
    private Long clientId;


    public Report() {
        // Default constructor
    }

    public Report(String description, Long clientId, Parking parking) {
        this.description = description;
        this.clientId = clientId;
        this.parking = parking;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
