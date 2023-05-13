package com.travel.trip.tide.location.model;

import com.travel.trip.tide.location.entity.LocationOpeningHours;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DetailedLocationResponseModel extends RepresentationModel<DetailedLocationResponseModel> {

    private String placeId;
    private String address;
    private String name;
    private String priceLevel;
    private long rating;
    private String internationalPhoneNumber;
    private LocationOpeningHours openHours;
    private List<String> photos;
    private String website;

}
