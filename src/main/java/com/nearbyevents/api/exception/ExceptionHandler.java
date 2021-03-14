package com.nearbyevents.api.exception;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler {
  private static final String ERROR_MESSAGE_FORMAT = "{\"error\":\"%s\"}";

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    if (ex instanceof NearbyEventsException) {
      final HttpStatus httpStatus =
          Optional.ofNullable(((NearbyEventsException) ex).getHttpStatus()).orElse(HttpStatus.NOT_FOUND);
      return produceErrorWithMessage(exchange, ex, httpStatus);
    }
    return Mono.error(ex);
  }

  private Mono<Void> produceErrorWithMessage(ServerWebExchange exchange, Throwable ex, HttpStatus httpStatus) {
    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
    final DataBuffer buffer = getBufferWithMessage(exchange, ex);

    exchange.getResponse().setStatusCode(httpStatus);
    return exchange.getResponse().writeWith(Flux.just(buffer));
  }

  private DataBuffer getBufferWithMessage(ServerWebExchange exchange, Throwable ex) {
    return exchange
        .getResponse()
        .bufferFactory()
        .wrap(
            (String.format(ERROR_MESSAGE_FORMAT, ex.getMessage() != null ? ex.getMessage() : ""))
                .getBytes(StandardCharsets.UTF_8));
  }
}
