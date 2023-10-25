package com.example.parkingbigservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key generated automatically
    private String username;
    private String password;
    private String email;
    private String image; // Add the 'image' field to match the SQL table
    private int xp;
    private int level;
    @Column(name = "bank_account")
    private String bankAccount; // Nullable attribute
    @Getter
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private PremiumSubscription premiumSubscription;

    // Constructors, getters, and setters

    public Client() {
        // Default constructor
    }

    public Client(String username, String password, String email) {
        this.username = username;
        setPassword(password);
        this.email = email;
    }

    // Getters and setters for other attributes

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }


    public void setPremiumSubscription(PremiumSubscription premiumSubscription) {
        this.premiumSubscription = premiumSubscription;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                '}';
    }
}
