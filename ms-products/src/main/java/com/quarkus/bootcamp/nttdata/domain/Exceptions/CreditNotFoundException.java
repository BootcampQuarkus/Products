package com.quarkus.bootcamp.nttdata.domain.Exceptions;

public class CreditNotFoundException extends Exception {
  public CreditNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
