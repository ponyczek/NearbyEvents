package com.nearbyevents.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "berlinde")
@Getter
@Setter
public class BerlinDeConfig {
  private String url;
}
