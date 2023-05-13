package com.travel.trip.tide.location.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationOpeningHours {

    @JsonProperty("open_now")
    private String openNow;
    @JsonProperty("weekday_text")
    private List<String> weekDayText;

}
