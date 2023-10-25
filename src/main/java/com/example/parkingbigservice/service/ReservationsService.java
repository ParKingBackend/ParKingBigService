package com.example.parkingbigservice.service;

import com.example.parkingbigservice.model.Reservations;
import com.example.parkingbigservice.repository.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationsService {

    private final ReservationsRepository reservationsRepository;

    @Autowired
    public ReservationsService(ReservationsRepository reservationsRepository) {
        this.reservationsRepository = reservationsRepository;
    }

    public Reservations createReservations(Reservations reservations) {

        return reservationsRepository.save(reservations);
    }
    public Reservations findById(Long clientId) {
        return reservationsRepository.findById(clientId).orElse(null);
    }
}
