package com.quarkus.bootcamp.nttdata.domain;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.BodyCorporateD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.NaturalPersonD;
import com.quarkus.bootcamp.nttdata.infraestructure.resource.IBodyCorporateApi;
import com.quarkus.bootcamp.nttdata.infraestructure.resource.INaturalPersonApi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class Util {

  @RestClient
  INaturalPersonApi naturalPersonApi;
  @RestClient
  IBodyCorporateApi bodyCorporateApi;

  public boolean validateCustomer(Long customerId) {
    BodyCorporateD bodyCorporate = bodyCorporateApi.getById(customerId);
    if (bodyCorporate.getId() != null) return true;
    NaturalPersonD naturalPerson = naturalPersonApi.getById(customerId);
    if (naturalPerson.getId() != null) return true;
    throw new NotFoundException("Customer not found.");
  }
}
