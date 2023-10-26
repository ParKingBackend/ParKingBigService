package com.example.parkingbigservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


    public Report() {
        // Default constructor
    }

    public Report(String description, Client client, Parking parking) {
        this.description = description;
        this.client = client;
        this.parking = parking;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", description='" + description +
                ", clientId=" + client +
                '}';
    }
}
