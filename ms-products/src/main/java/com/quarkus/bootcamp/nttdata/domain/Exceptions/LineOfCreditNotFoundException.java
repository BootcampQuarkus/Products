package com.quarkus.bootcamp.nttdata.domain.Exceptions;

public class LineOfCreditNotFoundException extends Exception {
  public LineOfCreditNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
