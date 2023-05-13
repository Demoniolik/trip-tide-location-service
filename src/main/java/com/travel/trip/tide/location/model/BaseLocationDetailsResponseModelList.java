package com.travel.trip.tide.location.model;

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
public class BaseLocationDetailsResponseModelList extends RepresentationModel<BaseLocationDetailsResponseModelList> {

    private List<BaseLocationDetailsResponseModel> candidates;

}
