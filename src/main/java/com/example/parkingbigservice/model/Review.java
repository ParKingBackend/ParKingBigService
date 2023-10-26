package com.example.parkingbigservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Double rating;
    @Column(name = "posted_time")
    private LocalDateTime postedTime;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

    // Constructors, getters, and setters

    public Review() {
        // Default constructor
    }
    public Review(String title, String description, Double rating, Client client, Parking parking) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.client = client;
        this.parking = parking;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", postedTime=" + postedTime +
                '}';
    }
}
