package com.nearbyevents.api.dtos.berlinde;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BerlinDeEvent {
  private String district;
  private String designation;
  private String street;
  private String postCode;
  private String fromDate;
  private String toDate;
  private String time;
  private String organiser;
  private String remarks;

  @JsonGetter("district")
  public String getDistrict() {
    return district;
  }

  @JsonSetter("bezirk")
  public void setDistrict(String district) {
    this.district = district;
  }

  @JsonGetter("designation")
  public String getDesignation() {
    return designation;
  }

  @JsonSetter("bezeichnung")
  public void setDesignation(String designation) {
    this.designation = designation;
  }

  @JsonGetter("street")
  public String getStreet() {
    return street;
  }

  @JsonSetter("strasse")
  public void setStreet(String street) {
    this.street = street;
  }

  @JsonGetter("postcode")
  public String getPostCode() {
    return postCode;
  }

  @JsonSetter("plz")
  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  @JsonGetter("fromDate")
  public String getFromDate() {
    return fromDate;
  }

  @JsonSetter("von")
  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  @JsonGetter("toDate")
  public String getToDate() {
    return toDate;
  }

  @JsonSetter("bis")
  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  @JsonGetter("time")
  public String getTime() {
    return time;
  }

  @JsonSetter("zeit")
  public void setTime(String time) {
    this.time = time;
  }

  @JsonGetter("organiser")
  public String getOrganiser() {
    return organiser;
  }

  @JsonSetter("veranstalter")
  public void setOrganiser(String organiser) {
    this.organiser = organiser;
  }

  @JsonGetter("remarks")
  public String getRemarks() {
    return remarks;
  }

  @JsonSetter("bemerkungen")
  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
}
