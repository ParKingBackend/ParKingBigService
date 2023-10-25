package com.example.parkingbigservice.service;

import com.example.parkingbigservice.model.Review;
import com.example.parkingbigservice.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }
    public Review findById(Long clientId) {
        return reviewRepository.findById(clientId).orElse(null);
    }

}
