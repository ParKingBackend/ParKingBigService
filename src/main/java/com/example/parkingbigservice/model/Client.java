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
    private Long id;
    private String username;
    private String password;
    private String email;
    private String image;
    private Integer xp;
    private Integer level;
    @Getter
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private PremiumSubscription premiumSubscription;


    public Client() {
    }

    public Client(String username, String password, String email) {
        this.username = username;
        setPassword(password);
        this.email = email;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
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
                '}';
    }
}
