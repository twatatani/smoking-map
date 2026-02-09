package com.example.smokingspotfinder.dto;

import com.example.smokingspotfinder.entity.SmokingPoint;

public class SmokingPointDistanceDto {
	private SmokingPoint sp;
	private Double distance;
	
	public SmokingPointDistanceDto(SmokingPoint sp,Double distance) {
		this.sp = sp;
		this.distance = distance;
	}
	
	public SmokingPoint getSp() {
		return this.sp;
	}
	public Double getDistance() {
		return this.distance;
	}
}
