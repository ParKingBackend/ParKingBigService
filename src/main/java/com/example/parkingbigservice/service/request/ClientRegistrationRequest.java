package com.example.parkingbigservice.service.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRegistrationRequest {
    private String username;
    private String password;
    private String email;
    private String image;
    // Constructors

    public ClientRegistrationRequest() {
        // Default constructor
    }

    public ClientRegistrationRequest(String username, String password, String email, String image) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
    }
}
