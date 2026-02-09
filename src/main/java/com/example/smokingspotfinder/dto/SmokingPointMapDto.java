package com.example.smokingspotfinder.dto;

import lombok.Data;

@Data
public class SmokingPointMapDto {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
}
