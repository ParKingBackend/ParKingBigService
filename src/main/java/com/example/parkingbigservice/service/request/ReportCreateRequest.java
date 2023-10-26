package com.example.parkingbigservice.service.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportCreateRequest {
    private Long clientId;
    private String description;

}
