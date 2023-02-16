package com.quarkus.bootcamp.nttdata.health;

import com.quarkus.bootcamp.nttdata.infraestructure.resource.IBodyCorporateApi;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Liveness
public class BodyCorporateApiHealth implements HealthCheck {

  @Inject
  @RestClient
  IBodyCorporateApi api;

  @Override
  public HealthCheckResponse call() {
    api.getAll();
    return HealthCheckResponse.named("Body Corporate APIs").up().build();
  }
}
