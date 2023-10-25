package com.example.parkingbigservice.service.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationCreateRequest {
    private Long clientId;
    private LocalDateTime endTime;
}
