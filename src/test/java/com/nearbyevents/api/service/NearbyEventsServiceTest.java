package com.nearbyevents.api.service;

import com.nearbyevents.api.client.BerlinDeApiClient;
import com.nearbyevents.api.dtos.berlinde.BerlinDeResponse;
import com.nearbyevents.api.exception.NearbyEventsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NearbyEventsServiceTest {

  @Mock private BerlinDeApiClient berlinDeApiClient;
  @InjectMocks private NearbyEventsService nearbyEventsService;

  @Test
  public void shouldGetEventsIfTheGivenCityIsBerlin() {
    final String city = "berlin";
    when(berlinDeApiClient.getEvents()).thenReturn(Mono.just(mock(BerlinDeResponse.class)));
    nearbyEventsService.getEvents(city);
    verify(berlinDeApiClient).getEvents();
  }

  @Test
  public void shouldThrowAnErrorIfDifferentCityIsProvided() {
    final String city = "london";
    final String errorMsg = "At the moment this api only provides events for Berlin.";
    when(berlinDeApiClient.getEvents())
        .thenReturn(Mono.error(new NearbyEventsException(errorMsg, HttpStatus.NOT_FOUND)));
    final Mono<BerlinDeResponse> events = berlinDeApiClient.getEvents();
    validateRequest(events, errorMsg);
    nearbyEventsService.getEvents(city);
  }

  private void validateRequest(final Mono<BerlinDeResponse> events, final String expectedMessage) {
    StepVerifier.create(events)
        .expectErrorMatches(
            throwable -> throwable instanceof NearbyEventsException && throwable.getMessage().equals(expectedMessage))
        .verify();
  }
}
