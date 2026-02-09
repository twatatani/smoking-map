package com.example.smokingspotfinder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NominatimResponse {

    @JsonProperty("lat")
    private String lat;

    @JsonProperty("lon")
    private String lon;

    @JsonProperty("display_name")
    private String displayName;

    public String getLatitude() {
        return lat;
    }

    public String getLongitude() {
        return lon;
    }

    public String getDisplayName() {
        return displayName;
    }
}
