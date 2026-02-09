package com.example.smokingspotfinder.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.smokingspotfinder.dto.LatLng;
import com.example.smokingspotfinder.dto.NominatimResponse;
import com.example.smokingspotfinder.dto.ReverseNominatimResponse;
import com.example.smokingspotfinder.service.GeocodingService;




@Controller
@RequestMapping("/geocode")
public class GeocodingController {
	private final GeocodingService geocodingService;
	public GeocodingController(GeocodingService geocodingService) {
		this.geocodingService=geocodingService;
	}
	
	@GetMapping
	public String showForm() {
		return "geocode-form";
	}
	@PostMapping
	public String search(@RequestParam String address,Model model) {
			NominatimResponse[] res = geocodingService.search(address); 
			if(res.length == 0) {
				model.addAttribute("error", "住所が見つかりませんでした");
				return "geocode-form";
			}
			double latitude = Double.parseDouble(res[0].getLatitude());
			double longitude = Double.parseDouble(res[0].getLongitude());
			
			model.addAttribute("latitude", latitude);
			model.addAttribute("longitude", longitude);
			model.addAttribute("displayName", res[0].getDisplayName());
		return "geocode-result";
	}
	
	@PostMapping("/api/search-address")
	@ResponseBody
	public Object searchAddressApi(@RequestParam String address) {
	    NominatimResponse[] res = geocodingService.search(address);
	    if (res == null || res.length == 0) {
	        return Map.of("error", "not_found");
	    }
	    double latitude = Double.parseDouble(res[0].getLatitude());
	    double longitude = Double.parseDouble(res[0].getLongitude());
	    return new LatLng(latitude, longitude);
	}

	@PostMapping("/api/search-latlng")
	@ResponseBody
	public ResponseEntity<?> searchLatLng(@RequestParam double lat,@RequestParam double lng) {
		ReverseNominatimResponse rnr = geocodingService.reverse(lat,lng);
		if(rnr == null) {
			return ResponseEntity .badRequest() .body(Map.of("error", "not_found"));
		}
		return ResponseEntity.ok(rnr);
	}
	
}
