package com.example.parkingbigservice.repository;

import com.example.parkingbigservice.model.Parking;
import com.example.parkingbigservice.model.Report;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{
    @Query("SELECT r FROM Report r WHERE r.parking = :parking")
    List<Report> findByParking(@Param("parking") Parking parking);
}
