package com.example.smokingspotfinder.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SmokingPoint {
	private Long id;
	
	@NotBlank(message="名称は必須です")
	private String name;
	@NotNull(message="緯度は必須です")
	private Double latitude;
	@NotNull(message="経度は必須です")
	private Double longitude;
	
	private String openTime;
	private String closeTime;
	
	private String address; 
	private String description;
	/** 元データ */
	private String rawDescription;
}
