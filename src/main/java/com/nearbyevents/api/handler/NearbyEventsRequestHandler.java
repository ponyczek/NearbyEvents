package com.nearbyevents.api.handler;

import com.nearbyevents.api.service.NearbyEventsService;
import com.nearbyevents.api.utils.NearbyEventsRequestValidator;
import com.nearbyevents.api.dtos.berlinde.BerlinDeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class NearbyEventsRequestHandler {
  private static final String CITY_PARAM = "city";
  private final NearbyEventsService nearbyEventsService;
  private final NearbyEventsRequestValidator validator;

  public Mono<ServerResponse> getEventsForCity(final ServerRequest request) {
    final String city = request.pathVariable(CITY_PARAM);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(getEventsForCity(city), BerlinDeResponse.class);
  }

  private Mono<BerlinDeResponse> getEventsForCity(final String city) {
    return validator.validateCity(city).flatMap(nearbyEventsService::getEvents);
  }
}
