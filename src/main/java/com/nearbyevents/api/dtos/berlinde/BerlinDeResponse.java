package com.nearbyevents.api.dtos.berlinde;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class BerlinDeResponse {
  private List<BerlinDeEvent> events;

  @JsonGetter("events")
  public List<BerlinDeEvent> getEvents() {
    return events;
  }

  @JsonSetter("index")
  public void setEventsList(List<BerlinDeEvent> events) {
    this.events = events;
  }
}
