package com.quarkus.bootcamp.nttdata.health;

import com.quarkus.bootcamp.nttdata.infraestructure.resource.INaturalPersonApi;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Liveness
public class NaturalPersonApiHealth implements HealthCheck {

  @Inject
  @RestClient
  INaturalPersonApi api;

  @Override
  public HealthCheckResponse call() {
    api.getAll();
    return HealthCheckResponse.named("NaturalPerson APIs").up().build();
  }
}
