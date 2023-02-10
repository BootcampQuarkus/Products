package com.quarkus.bootcamp.nttdata.domain.mapper;

import com.quarkus.bootcamp.nttdata.domain.entity.LineOfCredit;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.LineOfCreditD;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class LineOfCreditMapperTest {
  @Inject
  LineOfCreditMapper mapper;

  @Test
  public void toDtoNull() {
    LineOfCredit lineOfCredit = null;
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toDto(lineOfCredit));
  }

  @Test
  public void toEntityNull() {
    LineOfCreditD lineOfCreditD = null;
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toEntity(lineOfCreditD));
  }

  @Test
  public void toDtoReturnDto() {
    LineOfCredit lineOfCredit = new LineOfCredit();
    LineOfCreditD actual = mapper.toDto(lineOfCredit);
    Assertions.assertInstanceOf(LineOfCreditD.class, actual);
  }

  @Test
  public void toEntityReturnEntity() {
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    LineOfCredit actual = mapper.toEntity(lineOfCreditD);
    Assertions.assertInstanceOf(LineOfCredit.class, actual);
  }

  @Test
  public void toDtoVoid() {
    LineOfCredit lineOfCredit = new LineOfCredit();
    LineOfCreditD expected = new LineOfCreditD();
    LineOfCreditD actual = mapper.toDto(lineOfCredit);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void toEntityVoid() {
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    LineOfCredit expected = new LineOfCredit();
    LineOfCredit actual = mapper.toEntity(lineOfCreditD);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void toDtoValid() {
    // Variables
    Double amount = 500.00;
    Long customerId = 101L;
    Double available = 300.00;
    Double cost = 200.00;
    String closingDate = "10";
    String paymentDueDate = "10";

    // Input
    LineOfCredit lineOfCredit = new LineOfCredit();
    lineOfCredit.setAmount(amount);
    lineOfCredit.setCutomerId(customerId);
    lineOfCredit.setAvailable(available);
    lineOfCredit.setCosts(cost);
    lineOfCredit.setClosingDate(closingDate);
    lineOfCredit.setPaymentDueDate(paymentDueDate);

    // Resultado esperado
    LineOfCreditD expected = new LineOfCreditD();
    expected.setAmount(amount);
    expected.setCutomerId(customerId);
    expected.setAvailable(available);
    expected.setCosts(cost);
    expected.setClosingDate(closingDate);
    expected.setPaymentDueDate(paymentDueDate);

    // Ejecución
    LineOfCreditD actual = mapper.toDto(lineOfCredit);

    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void toEntityValid() {
    // Variables
    Double amount = 500.00;
    Long customerId = 101L;
    Double available = 300.00;
    Double cost = 200.00;
    String closingDate = "10";
    String paymentDueDate = "10";

    // Input
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    lineOfCreditD.setAmount(amount);
    lineOfCreditD.setCutomerId(customerId);
    lineOfCreditD.setAvailable(available);
    lineOfCreditD.setCosts(cost);
    lineOfCreditD.setClosingDate(closingDate);
    lineOfCreditD.setPaymentDueDate(paymentDueDate);

    // Resultado esperado
    LineOfCredit expected = new LineOfCredit();
    expected.setAmount(amount);
    expected.setCutomerId(customerId);
    expected.setAvailable(available);
    expected.setCosts(cost);
    expected.setClosingDate(closingDate);
    expected.setPaymentDueDate(paymentDueDate);

    // Ejecución
    LineOfCredit actual = mapper.toEntity(lineOfCreditD);

    Assertions.assertEquals(expected, actual);
  }

}