package com.travel.trip.tide.location.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.trip.tide.location.entity.DetailedLocation;
import com.travel.trip.tide.location.entity.DetailedLocationResult;
import com.travel.trip.tide.location.entity.LocationPhoto;
import com.travel.trip.tide.location.exception.RequestCallFailedException;
import com.travel.trip.tide.location.model.BaseLocationDetailsResponseModelList;
import com.travel.trip.tide.location.model.DetailedLocationResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

import static com.travel.trip.tide.location.constants.GoogleMapsApiConstants.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class LocationService {

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    @Value("${service.location.google.maps.api.host}")
    private String host;
    @Value("${service.location.google.maps.api.key}")
    private String apiKey;

    public BaseLocationDetailsResponseModelList getLocationDetails(
            String location, String language, String locationBias) {

        var client = new OkHttpClient.Builder()
                .build();
        var parametrizedRequestUrl = buildParametrizedQueryUrlForFindPlaceFromText(
                location, language, locationBias
        );
        var request = buildRequest(parametrizedRequestUrl);

        try (var response = client.newCall(request).execute()) {
            var responseBody = Objects.requireNonNull(response.body()).string();

            return objectMapper
                    .readValue(responseBody, BaseLocationDetailsResponseModelList.class);
        } catch (IOException e) {
            log.error(e);
            throw new RequestCallFailedException(
                    "maps.api.place.findplacefromtext.failed",
                    e.getLocalizedMessage()
            );
        }
    }

    public DetailedLocationResponseModel getDetailedLocationById(
            String placeId, String language) {
        var client = new OkHttpClient.Builder()
                .build();

        var parametrizedRequestUrl = buildParametrizedQueryForPlaceDetail(
                placeId, language
        );

        var request = buildRequest(parametrizedRequestUrl);

        try (var response = client.newCall(request).execute()) {
            var responseBody = Objects.requireNonNull(response.body()).string();

            var detailedLocationResult = objectMapper
                    .readValue(responseBody, DetailedLocation.class)
                    .getResult();

            return mapResponseToDetailedLocationResponseModel(detailedLocationResult);
        } catch (IOException e) {
            log.error(e);
            throw new RequestCallFailedException(
                    "maps.api.place.details.failed",
                    e.getLocalizedMessage()
            );
        }
    }

    @NotNull
    private DetailedLocationResponseModel mapResponseToDetailedLocationResponseModel(DetailedLocationResult detailedLocationResult) {
        var detailedLocationResponseModel = modelMapper
                .map(detailedLocationResult, DetailedLocationResponseModel.class);

        detailedLocationResponseModel.setPhotos(
                detailedLocationResult.getPhotos().stream()
                        .map(this::mapLocationPhotoToUrl)
                        .toList()
        );
        return detailedLocationResponseModel;
    }

    @NotNull
    private static Request buildRequest(HttpUrl parametrizedRequestUrl) {
        return new Request.Builder()
                .url(parametrizedRequestUrl)
                .addHeader("Accept", "application/json")
                .build();
    }

    private String mapLocationPhotoToUrl(LocationPhoto photo) {
        return new HttpUrl.Builder()
                .scheme(HTTPS_SCHEMA)
                .host(host)
                .addPathSegment(MAPS_REQUEST_PATH_SEGMENT)
                .addPathSegment(API_REQUEST_PATH_SEGMENT)
                .addPathSegment(PLACE_REQUEST_PATH_SEGMENT)
                .addPathSegment(PHOTO_REQUEST_PATH_SEGMENT)
                .addQueryParameter(PHOTO_REFERENCE_QUERY_PARAM_NAME, photo.getPhotoReference())
                .addQueryParameter(SENSOR_QUERY_PARAM_NAME, SENSOR_QUERY_PARAM_VALUE)
                .addQueryParameter(MAX_HEIGHT_QUERY_PARAM_VALUE, photo.getHeight())
                .addQueryParameter(MAX_WIDTH_QUERY_PARAM_VALUE, photo.getWidth())
                .addQueryParameter(GOOGLE_API_KEY_QUERY_PARAM_NAME, GOOGLE_API_KEY_QUERY_PARAM_PLACEHOLDER_VALUE)
                .build()
                .toString();
    }

    @NotNull
    private HttpUrl buildParametrizedQueryUrlForFindPlaceFromText(String location, String language, String locationBias) {
        return new HttpUrl.Builder()
                .scheme(HTTPS_SCHEMA)
                .host(host)
                .addPathSegment(MAPS_REQUEST_PATH_SEGMENT)
                .addPathSegment(API_REQUEST_PATH_SEGMENT)
                .addPathSegment(PLACE_REQUEST_PATH_SEGMENT)
                .addPathSegment(FIND_PLACE_FROM_TEXT_REQUEST_PATH_SEGMENT)
                .addPathSegment(JSON_FORMAT_REQUEST_PATH_SEGMENT)
                .addQueryParameter(INPUT_QUERY_PARAM_NAME, location)
                .addQueryParameter(INPUT_TYPE_QUERY_PARAM_NAME, INPUT_TYPE_QUERY_PARAM_VALUE)
                .addQueryParameter(FIELDS_QUERY_PARAM_NAME, BASIC_LOCATION_DETAILS_FIELDS)
                .addQueryParameter(LANGUAGE_QUERY_PARAM_NAME, language)
                .addQueryParameter(LOCATION_BIAS_QUERY_PARAM_NAME, locationBias)
                .addQueryParameter(GOOGLE_API_KEY_QUERY_PARAM_NAME, apiKey)
                .build();
    }

    @NotNull
    private HttpUrl buildParametrizedQueryForPlaceDetail(String placeId, String language) {
        return new HttpUrl.Builder()
                .scheme(HTTPS_SCHEMA)
                .host(host)
                .addPathSegment(MAPS_REQUEST_PATH_SEGMENT)
                .addPathSegment(API_REQUEST_PATH_SEGMENT)
                .addPathSegment(PLACE_REQUEST_PATH_SEGMENT)
                .addPathSegment(DETAILS_REQUEST_PATH_SEGMENT)
                .addPathSegment(JSON_FORMAT_REQUEST_PATH_SEGMENT)
                .addQueryParameter(PLACE_ID_QUERY_PARAM_NAME, placeId)
                .addQueryParameter(FIELDS_QUERY_PARAM_NAME, DETAILED_LOCATION_DETAILS_FIELDS)
                .addQueryParameter(LANGUAGE_QUERY_PARAM_NAME, language)
                .addQueryParameter(GOOGLE_API_KEY_QUERY_PARAM_NAME, apiKey)
                .build();
    }
}
