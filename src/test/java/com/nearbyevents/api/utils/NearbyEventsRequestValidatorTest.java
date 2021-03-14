package com.nearbyevents.api.utils;

import com.nearbyevents.api.exception.NearbyEventsException;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NearbyEventsRequestValidatorTest {
  private final NearbyEventsRequestValidator validator = new NearbyEventsRequestValidator();

  @Test
  public void shouldAcceptSingleWordCityAsParam() {
    final String singleWordCity = "Berlin";
    final Mono<String> result = validator.validateCity(singleWordCity);
    assertEquals(singleWordCity, result.block());
  }

  @Test
  public void shouldAcceptTwoWordCityAsParam() {
    final String singleWordCity = "New York";
    final Mono<String> result = validator.validateCity(singleWordCity);
    assertEquals(singleWordCity, result.block());
  }

  @Test
  public void shouldAcceptTwoWordCityWithDashAsParam() {
    String singleWordCity = "New-York";
    Mono<String> result = validator.validateCity(singleWordCity);
    assertEquals(singleWordCity, result.block());
  }

  @Test
  public void shouldThrowAnExceptionIfInvalidNameWasGiven() {
    final String invalidCityName = "1232kcity";
    final Mono<String> validatedCityMono = validator.validateCity(invalidCityName);
    performValidation(validatedCityMono);
  }

  private void performValidation(final Mono<String> city) {
    final String expectedErrorMessage = "Invalid city name provided.";
    StepVerifier.create(city)
        .expectErrorMatches(
            throwable ->
                throwable instanceof NearbyEventsException && throwable.getMessage().equals(expectedErrorMessage))
        .verify();
  }
}
