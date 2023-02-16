package com.quarkus.bootcamp.nttdata.domain.Exceptions;

public class AccountNotFoundException extends Exception {
  public AccountNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
