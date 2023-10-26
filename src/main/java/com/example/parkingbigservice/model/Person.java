package com.example.parkingbigservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String surname;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;


    public Person() {
    }

    public Person(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                '\'' +
                '}';
    }
}
