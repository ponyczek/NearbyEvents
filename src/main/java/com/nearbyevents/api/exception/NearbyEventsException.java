package com.nearbyevents.api.exception;

import org.springframework.http.HttpStatus;

public class NearbyEventsException extends RuntimeException {

  private HttpStatus httpStatus;

  public NearbyEventsException(final String message) {
    super(message);
  }

  public NearbyEventsException(final String message, final HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
