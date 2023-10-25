package com.example.parkingbigservice.repository;

import com.example.parkingbigservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.bankAccount = :bankAccount WHERE c.id = :clientId")
    void addBankAccount(@Param("clientId") Long clientId, @Param("bankAccount") String bankAccount);
    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.image = :image WHERE c.id = :clientId")
    void addImage(@Param("clientId") Long clientId, @Param("image") String image);
}
