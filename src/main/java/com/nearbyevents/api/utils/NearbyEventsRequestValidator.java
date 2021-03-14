package com.nearbyevents.api.utils;

import com.nearbyevents.api.exception.NearbyEventsException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class NearbyEventsRequestValidator {
  public static final String ALLOWED_CITIES_REGEX_PATTERN = "^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$";

  public Mono<String> validateCity(final String city) {
    return city.matches(ALLOWED_CITIES_REGEX_PATTERN)
        ? Mono.just(city)
        : Mono.error(new NearbyEventsException("Invalid city name provided.", HttpStatus.BAD_REQUEST));
  }
}
