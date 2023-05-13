package com.travel.trip.tide.location.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationPhoto {

    private String height;
    private String width;
    @JsonProperty("photo_reference")
    private String photoReference;

}
