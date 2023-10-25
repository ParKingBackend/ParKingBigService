package com.example.parkingbigservice.repository;

import com.example.parkingbigservice.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long>{
}
