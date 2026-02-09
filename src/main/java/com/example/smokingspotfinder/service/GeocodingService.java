package com.example.smokingspotfinder.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.smokingspotfinder.dto.NominatimResponse;
import com.example.smokingspotfinder.dto.ReverseNominatimResponse;

@Service
public class GeocodingService {
	private final RestTemplate restTemplate;
	public GeocodingService() { 
		this.restTemplate = new RestTemplate(); 
		this.restTemplate.getInterceptors().add((request, body, execution) -> { 
			request.getHeaders().add("User-Agent", "SmokingSpotFinder/1.0 (warumonova@gmail.com)"); 
			return execution.execute(request, body); 
			}); 
		}
	/** 住所から緯度経度検索 */
	public NominatimResponse[] search(String address) {
		String encoded = URLEncoder.encode(address, StandardCharsets.UTF_8);
		String url = String.format(
			    "https://nominatim.openstreetmap.org/search?q=%s&format=json&limit=1",
			    encoded
			);
		return restTemplate.getForObject(url, NominatimResponse[].class);
	}
	
	/** 緯度経度から住所検索 */
	public ReverseNominatimResponse reverse(double lat, double lon) { 
		String url = String.format( "https://nominatim.openstreetmap.org/reverse?format=json&lat=%s&lon=%s&addressdetails=1&zoom=18", lat, lon );
		return restTemplate.getForObject(url, ReverseNominatimResponse.class); 
		}
}
