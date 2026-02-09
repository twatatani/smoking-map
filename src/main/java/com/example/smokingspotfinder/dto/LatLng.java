package com.example.smokingspotfinder.dto;

import lombok.Data;

@Data
public class LatLng {
	private Double latitude; 
	private Double longitude;
	
	public LatLng(Double latitude, Double longitude) { 
		this.latitude = latitude; 
		this.longitude = longitude; 
		}
}
