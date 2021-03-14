package com.nearbyevents.api.client;

import com.nearbyevents.api.exception.NearbyEventsException;
import com.nearbyevents.api.config.BerlinDeConfig;
import com.nearbyevents.api.dtos.berlinde.BerlinDeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(BerlinDeConfig.class)
public class BerlinDeApiClient {
  private final WebClient client = WebClient.create();
  private final BerlinDeConfig berlinDeConfig;

  public Mono<BerlinDeResponse> getEvents() {
    return client
        .get()
        .uri(berlinDeConfig.getUrl())
        .retrieve()
        .onStatus(
            HttpStatus::is5xxServerError,
            clientResponse -> Mono.error(new NearbyEventsException("Can't obtain events data from berlin.de")))
        .bodyToMono(BerlinDeResponse.class);
  }
}
