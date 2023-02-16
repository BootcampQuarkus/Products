package com.quarkus.bootcamp.nttdata.domain.Exceptions;

public class CustomerNotFoundException extends Exception {
  public CustomerNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
