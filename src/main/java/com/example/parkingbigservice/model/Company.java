package com.example.parkingbigservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key generated automatically
    private String compName;
    private String bio;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // Constructors, getters, and setters

    public Company() {
        // Default constructor
    }

    public Company(String compName, String bio) {
        this.compName = compName;
        this.bio = bio;
    }

    // Getters and setters for other attributes

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", compName='" + compName + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
