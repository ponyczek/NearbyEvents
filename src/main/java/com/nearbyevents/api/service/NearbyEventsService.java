package com.nearbyevents.api.service;

import com.nearbyevents.api.client.BerlinDeApiClient;
import com.nearbyevents.api.dtos.berlinde.BerlinDeResponse;
import com.nearbyevents.api.exception.NearbyEventsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class NearbyEventsService {
  private static final String BERLIN = "berlin";
  private final BerlinDeApiClient berlinDeApiClient;

  public Mono<BerlinDeResponse> getEvents(final String city) {
    // At the moment the app is meant to only provide events for Berlin
    // In the future the app might support other locations by using different API such as ticketmaster, or predicthq
    // Therefore this validator would probably check if a sensible city name has been provided
    return city.equalsIgnoreCase(BERLIN)
        ? berlinDeApiClient.getEvents()
        : Mono.error(
            new NearbyEventsException("At the moment this api only provides events for Berlin.", HttpStatus.NOT_FOUND));
  }
}
