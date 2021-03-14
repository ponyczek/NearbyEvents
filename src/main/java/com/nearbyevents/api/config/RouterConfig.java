package com.nearbyevents.api.config;

import com.nearbyevents.api.handler.NearbyEventsRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
  private static final String API_V_1 = "/api/v1";
  private static final String EVENTS_PATH = "/events/{city}";

  @Bean
  public RouterFunction<ServerResponse> eventsRoutes(final NearbyEventsRequestHandler nearbyEventsRequestHandler) {
    return nest(
        path(API_V_1).and(accept(MediaType.APPLICATION_JSON)),
        route().GET(EVENTS_PATH, nearbyEventsRequestHandler::getEventsForCity).build());
  }
}
