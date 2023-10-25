package com.example.parkingbigservice.service.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ReviewCreateRequest {
    private Long clientId;
    private String title;
    private String description;
    private double rating;
    private LocalDateTime postedTime;
}
