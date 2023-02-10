package com.quarkus.bootcamp.nttdata.domain.mapper;

import com.quarkus.bootcamp.nttdata.domain.entity.Credit;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.CreditD;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class CreditMapperTest {
  @Inject
  CreditMapper mapper;

  @Test
  public void toDtoNull() {
    Credit credit = null;
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toDto(credit));
  }

  @Test
  public void toEntityNull() {
    CreditD creditD = null;
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toEntity(creditD));
  }

  @Test
  public void toDtoReturnDto() {
    Credit credit = new Credit();
    CreditD actual = mapper.toDto(credit);
    Assertions.assertInstanceOf(CreditD.class, actual);
  }

  @Test
  public void toEntityReturnEntity() {
    CreditD creditD = new CreditD();
    Credit actual = mapper.toEntity(creditD);
    Assertions.assertInstanceOf(Credit.class, actual);
  }

  @Test
  public void toDtoVoid() {
    Credit credit = new Credit();
    CreditD expected = new CreditD();
    CreditD actual = mapper.toDto(credit);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void toEntityVoid() {
    CreditD creditD = new CreditD();
    Credit expected = new Credit();
    Credit actual = mapper.toEntity(creditD);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void toDtoValid() {
    // Variables
    Double amount = 500.00;
    Long customerId = 101L;
    Double balance = 300.00;
    Integer due = 5;
    String paymentDueDate = "10";

    // Input
    Credit credit = new Credit();
    credit.setAmount(amount);
    credit.setCutomerId(customerId);
    credit.setBalance(balance);
    credit.setDues(due);
    credit.setPaymentDueDate(paymentDueDate);

    // Resultado esperado
    CreditD expected = new CreditD();
    expected.setAmount(amount);
    expected.setCutomerId(customerId);
    expected.setBalance(balance);
    expected.setDues(due);
    expected.setPaymentDueDate(paymentDueDate);

    // Ejecución
    CreditD actual = mapper.toDto(credit);

    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void toEntityValid() {
    // Variables
    Double amount = 500.00;
    Long customerId = 101L;
    Double balance = 300.00;
    Integer due = 5;
    String paymentDueDate = "10";

    // Input
    CreditD creditD = new CreditD();
    creditD.setAmount(amount);
    creditD.setCutomerId(customerId);
    creditD.setBalance(balance);
    creditD.setDues(due);
    creditD.setPaymentDueDate(paymentDueDate);

    // Resultado esperado
    Credit expected = new Credit();
    expected.setAmount(amount);
    expected.setCutomerId(customerId);
    expected.setBalance(balance);
    expected.setDues(due);
    expected.setPaymentDueDate(paymentDueDate);

    // Ejecución
    Credit actual = mapper.toEntity(creditD);

    Assertions.assertEquals(expected, actual);
  }
}