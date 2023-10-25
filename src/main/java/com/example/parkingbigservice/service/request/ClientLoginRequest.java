package com.example.parkingbigservice.service.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientLoginRequest {
    private String username;
    private String password;

    // Constructors

    public ClientLoginRequest() {
        // Default constructor
    }

    public ClientLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
