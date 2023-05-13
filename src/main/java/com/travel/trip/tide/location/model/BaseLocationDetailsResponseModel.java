package com.travel.trip.tide.location.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseLocationDetailsResponseModel {

    @JsonProperty("place_id")
    private String placeId;
    @JsonProperty("formatted_address")
    private String address;
    private String name;
    @JsonProperty("price_level")
    private String priceLevel;
    private long rating;


}
