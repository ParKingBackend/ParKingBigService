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

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.username = :username, c.password = :password, c.email = :email WHERE c.id = :clientId")
    void updateClient(Long clientId, String username, String password, String email);

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.xp = :xp WHERE c.id = :clientId")
    void updateXp(Long clientId, int xp);

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.level = :level WHERE c.id = :clientId")
    void updateLevel(Long clientId, int level);


    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.username = :username WHERE c.id = :clientId")
    void updateUsername(Long clientId, String username);

    Client findByEmail(String email);
}
