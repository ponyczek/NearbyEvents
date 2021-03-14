package com.nearbyevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class NearbyEventsApplication {

  public static void main(String[] args) {
    SpringApplication.run(NearbyEventsApplication.class, args);
  }
}
