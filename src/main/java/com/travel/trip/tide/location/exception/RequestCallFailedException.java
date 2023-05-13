package com.travel.trip.tide.location.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RequestCallFailedException extends RuntimeException {

    private final String errorCode;
    private final String errorDescription;

}
