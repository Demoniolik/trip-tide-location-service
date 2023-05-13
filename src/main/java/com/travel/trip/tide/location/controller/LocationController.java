package com.travel.trip.tide.location.controller;

import com.travel.trip.tide.location.model.BaseLocationDetailsResponseModelList;
import com.travel.trip.tide.location.model.DetailedLocationResponseModel;
import com.travel.trip.tide.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {

    private static final String GET_DETAILED_LOCATION_BY_PLACE_ID
            = "detailed location by place id";
    private static final String GET_BASE_LOCATION_DETAILS_BY_PLACE_ID
            = "base location";
    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<BaseLocationDetailsResponseModelList> getLocationDetails(
            @RequestParam String location,
            @RequestParam(defaultValue = "en", required = false) String language,
            @RequestParam(required = false) String locationBias
    ) {
        return ResponseEntity.ok(
                applyHateous(
                        locationService.getLocationDetails(location, language, locationBias)
                )
        );
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<DetailedLocationResponseModel> getDetailedLocationDetailsById(
            @PathVariable String placeId,
            @RequestParam(defaultValue = "en", required = false) String language
    ) {
        return ResponseEntity.ok(
                applyHateous(
                        locationService.getDetailedLocationById(placeId, language)
                ));
    }

    private DetailedLocationResponseModel applyHateous(
            DetailedLocationResponseModel locationResponseModel) {
        var slefLink = linkTo(LocationController.class)
                .slash(locationResponseModel.getPlaceId())
                .withSelfRel();
        var getLocationDetailsByPlaceId = linkTo((methodOn(LocationController.class)
                .getLocationDetails(
                        locationResponseModel.getName(), "en", ""
                )))
                .withRel(GET_BASE_LOCATION_DETAILS_BY_PLACE_ID);
        locationResponseModel.add(slefLink, getLocationDetailsByPlaceId);

        return locationResponseModel;
    }

    private BaseLocationDetailsResponseModelList applyHateous(
            BaseLocationDetailsResponseModelList locationResponseModel) {
        var slefLink = linkTo(methodOn(LocationController.class)
                .getLocationDetails(
                        locationResponseModel.getCandidates().get(0).getName(),
                        "en", ""
                )
        )
                .withSelfRel();
        var getLocationDetailsByPlaceId = linkTo((methodOn(LocationController.class)
                .getDetailedLocationDetailsById(locationResponseModel
                        .getCandidates().get(0).getPlaceId(), "en")))
                .withRel(GET_DETAILED_LOCATION_BY_PLACE_ID);
        locationResponseModel.add(slefLink, getLocationDetailsByPlaceId);

        return locationResponseModel;
    }

}
