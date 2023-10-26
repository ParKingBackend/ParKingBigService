package com.example.parkingbigservice.service.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ParkingRequest {
    private String address;
    private Boolean isDisabled;
    private Boolean isPremium;
    private Integer maxSpotsCount;
    private BigDecimal price;
    private Integer spotsTaken;
    private Long partnerId;

    public ParkingRequest() {
    }

}
