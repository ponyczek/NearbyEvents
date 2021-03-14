package com.nearbyevents;

import com.nearbyevents.api.dtos.berlinde.BerlinDeEvent;
import com.nearbyevents.api.dtos.berlinde.BerlinDeResponse;
import com.nearbyevents.api.service.NearbyEventsService;
import com.nearbyevents.api.utils.NearbyEventsRequestValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class NearbyEventsIntegrationTest {
  private static final String EVENTS_PATH_PATTERN = "/api/v1/events/%s";
  @Autowired private WebTestClient webTestClient;

  @MockBean private NearbyEventsService nearbyEventsService;

  @MockBean private NearbyEventsRequestValidator validator;

  @Test
  public void shouldReturnAndEventsFromTheApi() {
    final String city = "berlin";
    final String uri = String.format(EVENTS_PATH_PATTERN, city);

    final String firstEventStreet = "Suarezstraße 58";
    final String firstEventDistrict = "Charlottenburg-Wilmersdorf";
    final String firstEventPostcode = "14057";
    final String firstEventDesignation = "11. Familiensportfest im Olympiapark Berlin";
    final String firstEventOrganiser = "Organiser1";
    final String firstEventRemarks = "Suarezstraße zwischen Kantstraße und Kaiserdamm";
    final BerlinDeEvent firstEvent =
        createBerlinDeEvent(
            firstEventStreet,
            firstEventDistrict,
            firstEventPostcode,
            firstEventDesignation,
            firstEventOrganiser,
            firstEventRemarks);

    final String secondEventStreet = "Wartenberger Straße";
    final String secondEventDistrict = "Lichtenberg";
    final String secondEventPostcode = "13053";
    final String secondEventDesignation = "Herbstfest Berlin-Hohenschönhausen";
    final String secondEventOrganiser = "organiser2";
    final String secondEventRemarks = "Parkplatz vor dem Kino Cine-Motion";
    final BerlinDeEvent secondEvent =
        createBerlinDeEvent(
            secondEventStreet,
            secondEventDistrict,
            secondEventPostcode,
            secondEventDesignation,
            secondEventOrganiser,
            secondEventRemarks);

    final BerlinDeResponse expectedResponse = new BerlinDeResponse();
    expectedResponse.setEventsList(List.of(firstEvent, secondEvent));

    when(validator.validateCity(city)).thenReturn(Mono.just(city));
    when(nearbyEventsService.getEvents(city)).thenReturn(Mono.just(expectedResponse));

    webTestClient
        .get()
        .uri(uri)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(BerlinDeResponse.class)
        .consumeWith(
            response -> {
              final BerlinDeEvent event1 = expectedResponse.getEvents().get(0);
              final BerlinDeEvent event2 = expectedResponse.getEvents().get(1);
              compareEvents(firstEvent, event1);
              compareEvents(secondEvent, event2);
            });
  }

  @Test
  public void shouldThrow400WhenCityIsInInvalidFormat() {
    final String city = "invalidFormatCity1236";
    final String uri = String.format(EVENTS_PATH_PATTERN, city);

    webTestClient
        .get()
        .uri(uri)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .is4xxClientError()
        .expectBody()
        .jsonPath("$.error")
        .isEqualTo("Invalid city name provided");
  }

  private void compareEvents(BerlinDeEvent firstEvent, BerlinDeEvent event1) {
    assertEquals(firstEvent.getStreet(), event1.getStreet());
    assertEquals(firstEvent.getDistrict(), event1.getDistrict());
    assertEquals(firstEvent.getPostCode(), event1.getPostCode());
    assertEquals(firstEvent.getDesignation(), event1.getDesignation());
    assertEquals(firstEvent.getOrganiser(), event1.getOrganiser());
    assertEquals(firstEvent.getRemarks(), event1.getRemarks());
  }

  private BerlinDeEvent createBerlinDeEvent(
      final String street,
      final String district,
      final String postCode,
      final String designation,
      final String organiser,
      final String remarks) {
    final BerlinDeEvent event = new BerlinDeEvent();
    event.setStreet(street);
    event.setDistrict(district);
    event.setPostCode(postCode);
    event.setDesignation(designation);
    event.setOrganiser(organiser);
    event.setRemarks(remarks);
    return event;
  }
}
