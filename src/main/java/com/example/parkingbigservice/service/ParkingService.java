package com.example.parkingbigservice.service;


import com.example.parkingbigservice.model.Parking;
import com.example.parkingbigservice.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public Parking createParking(Parking parking) {
        if (parking.getStartTime() == null) {
            parking.setStartTime(LocalDateTime.now());
        }
        return parkingRepository.save(parking);
    }

    public Parking findById(Long parkingId) {
        return parkingRepository.findById(parkingId).orElse(null);
    }

    public List<Parking> getAllClients() {
        return parkingRepository.findAll();
    }

    public boolean deleteParking(Long id) {
        if (parkingRepository.existsById(id)) {
            parkingRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void updateParking(Parking parking) {
        parkingRepository.save(parking);
    }
}
