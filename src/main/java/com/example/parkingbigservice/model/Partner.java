package com.example.parkingbigservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "partner")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Renamed to "id"
    private String name;
    private String bankAccount;

    public Partner() {
        // Default constructor
    }

    public Partner(String name, String bankAccount) {
        this.name = name;
        this.bankAccount = bankAccount;
    }
}