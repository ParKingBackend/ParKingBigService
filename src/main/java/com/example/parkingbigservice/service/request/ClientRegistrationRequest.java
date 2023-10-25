package com.example.parkingbigservice.service.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRegistrationRequest {
    private String username;
    private String password;
    private String email;

    // Constructors

    public ClientRegistrationRequest() {
        // Default constructor
    }

    public ClientRegistrationRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
