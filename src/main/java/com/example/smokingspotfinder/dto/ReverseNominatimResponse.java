package com.example.smokingspotfinder.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class ReverseNominatimResponse {
    private String displayName;
    private Address address;
    private String formattedAddress;
    @JsonCreator
    public ReverseNominatimResponse(@JsonProperty("display_name") String displayName,
            @JsonProperty("address") Address address) {
    	this.displayName = displayName;
    	this.address = address;
    	this.formattedAddress = formatAddress(address);
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address{
        private String neighbourhood;
        private String suburb;
        private String city;
        private String province;
        private String postcode;
        private String country;

        @JsonProperty("country_code")
        private String countryCode;

        @JsonProperty("ISO3166-2-lvl4")
        private String iso3166_2_lvl4;
    }
    
    private String formatAddress(Address address) {
    	if(address == null) {return null;}
    	StringBuilder sb = new StringBuilder();
    	if (address.getProvince() != null) { sb.append(address.getProvince());} 
    	if (address.getCity() != null) {sb.append(address.getCity());} 
    	if (address.getSuburb() != null) {sb.append(address.getSuburb());} 
    	if (address.getNeighbourhood() != null) {sb.append(address.getNeighbourhood());}
    	return sb.toString();
    }
}

