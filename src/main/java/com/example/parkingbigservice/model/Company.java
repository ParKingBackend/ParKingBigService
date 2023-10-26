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
    private Long id;
    private String compName;
    private String bio;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;


    public Company() {
    }

    public Company(String compName, String bio) {
        this.compName = compName;
        this.bio = bio;
    }


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
