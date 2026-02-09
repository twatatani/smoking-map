package com.example.smokingspotfinder.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smokingspotfinder.dto.SmokingPointMapDto;
import com.example.smokingspotfinder.service.SmokingPointService;

@RestController
@RequestMapping("/api")
public class SmokingPointApiController {
    private final SmokingPointService spService;
    public SmokingPointApiController(SmokingPointService spService) {
        this.spService = spService;
    }
    
    /** JSON(smokingpoint一覧) */
    @GetMapping("/smoking-points")
    public List<SmokingPointMapDto> getSmokingPoints() {
        return spService.findAllForMap();
    }
}
