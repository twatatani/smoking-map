package com.example.smokingspotfinder.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.example.smokingspotfinder.dto.NominatimResponse;

@ExtendWith(MockitoExtension.class)
class GeocodingServiceTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    GeocodingService geocodingService;

    @Test
    void testSearch() {

        NominatimResponse mockResponse = new NominatimResponse();
        ReflectionTestUtils.setField(mockResponse, "lat", "34.5");
        ReflectionTestUtils.setField(mockResponse, "lon", "135.4");
        ReflectionTestUtils.setField(mockResponse, "displayName", "大阪府堺市");

        NominatimResponse[] responses = { mockResponse };

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(NominatimResponse[].class)
        )).thenReturn(new ResponseEntity<>(responses, HttpStatus.OK));

        NominatimResponse[] result = geocodingService.search("大阪府堺市");

        assertEquals("34.5", result[0].getLatitude());
        assertEquals("135.4", result[0].getLongitude());
        assertEquals("大阪府堺市", result[0].getDisplayName());
    }
}
