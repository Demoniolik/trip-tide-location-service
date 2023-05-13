package com.travel.trip.tide.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpeningHoursResponseModel {

    private String openNow;
    private List<String> weekDayText;

}
