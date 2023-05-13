package com.travel.trip.tide.location.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailedLocationResult {

    @JsonProperty("place_id")
    private String placeId;
    @JsonProperty("formatted_address")
    private String address;
    private String name;
    @JsonProperty("price_level")
    private String priceLevel;
    private long rating;
    @JsonProperty("international_phone_number")
    private String internationalPhoneNumber;
    @JsonProperty("opening_hours")
    private LocationOpeningHours openHours;
    private List<LocationPhoto> photos;
    private String website;

}
