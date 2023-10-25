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
    private double rating;
    @Column(name = "posted_time")
    private LocalDateTime postedTime;
    private Long clientId;
    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

    // Constructors, getters, and setters

    public Review() {
        // Default constructor
    }
    public Review(String title, String description, double rating, Long clientId, Parking parking) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.clientId = clientId;
        this.parking = parking;
        this.postedTime = LocalDateTime.now(); // Set the posted time to the current local time
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
