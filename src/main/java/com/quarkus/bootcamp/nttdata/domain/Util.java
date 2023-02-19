package com.quarkus.bootcamp.nttdata.domain;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.CustomerNotFoundException;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.AccountD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.CreditD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.LineOfCreditD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.BodyCorporateD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.NaturalPersonD;
import com.quarkus.bootcamp.nttdata.infraestructure.resource.IBodyCorporateApi;
import com.quarkus.bootcamp.nttdata.infraestructure.resource.INaturalPersonApi;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class Util {

  @RestClient
  INaturalPersonApi naturalPersonApi;
  @RestClient
  IBodyCorporateApi bodyCorporateApi;

  public boolean validateCustomer(Long customerId) throws CustomerNotFoundException {
    BodyCorporateD bodyCorporate = bodyCorporateApi.getById(customerId);
    if (bodyCorporate.getId() != null) return true;
    NaturalPersonD naturalPerson = naturalPersonApi.getById(customerId);
    if (naturalPerson.getId() != null) return true;
    throw new CustomerNotFoundException("Customer not found.");
  }

  public boolean notDelete(AccountD accountD) {
    return accountD.getDeletedAt() == null;
  }

  public boolean notDelete(CreditD creditD) {
    return creditD.getDeletedAt() == null;
  }

  public boolean notDelete(LineOfCreditD lineOfCreditD) {
    return lineOfCreditD.getDeletedAt() == null;
  }
}
