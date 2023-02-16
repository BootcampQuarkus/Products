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

  /**
   * Cuando se envia un elemento null al metodo toDto debe retornar un NullPointerException.
   */
  @Test
  public void toDtoNull() {
    LineOfCreditD lineOfCreditD = null;
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toDto(lineOfCreditD));
  }

  /**
   * Cuando se envia un elemento null al metodo toEntity debe retornar un NullPointerException.
   */
  @Test
  public void toEntityNull() {
    LineOfCredit lineOfCredit = null;
    Assertions.assertThrows(NullPointerException.class, () -> mapper.toEntity(lineOfCredit));
  }

  /**
   * El metodo toDto debe retornar un Dto.
   */
  @Test
  public void toDtoReturnDto() {
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    Assertions.assertInstanceOf(LineOfCredit.class, mapper.toDto(lineOfCreditD));
  }

  /**
   * El metodo toDto debe retornar un Entity.
   */
  @Test
  public void toEntityReturnEntity() {
    LineOfCredit lineOfCredit = new LineOfCredit();
    Assertions.assertInstanceOf(LineOfCreditD.class, mapper.toEntity(lineOfCredit));
  }

  /**
   * Cuando se envia un elemento vacio al metodo toDto debe retornar un Dto vacio.
   */
  @Test
  public void toDtoVoid() {
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    LineOfCredit expected = new LineOfCredit();
    LineOfCredit actual = mapper.toDto(lineOfCreditD);
    Assertions.assertEquals(expected, actual);
  }

  /**
   * Cuando se envia un elemento vacio al metodo toEntity debe retornar un Entity vacio.
   */
  @Test
  public void toEntityVoid() {
    LineOfCredit lineOfCredit = new LineOfCredit();
    LineOfCreditD expected = new LineOfCreditD();
    LineOfCreditD actual = mapper.toEntity(lineOfCredit);
    Assertions.assertEquals(expected, actual);
  }

  /**
   * Cuando se envia un elemento valido al metodo toDto debe retornar un Dto con los mismos datos en los campos
   * amount, customerId, available, cost, closingDate y paymentDueDate.
   */
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
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    lineOfCreditD.setAmount(amount);
    lineOfCreditD.setCustomerId(customerId);
    lineOfCreditD.setAvailable(available);
    lineOfCreditD.setCosts(cost);
    lineOfCreditD.setClosingDate(closingDate);
    lineOfCreditD.setPaymentDueDate(paymentDueDate);

    // Resultado esperado
    LineOfCredit expected = new LineOfCredit();
    expected.setAmount(amount);
    expected.setCustomerId(customerId);
    expected.setAvailable(available);
    expected.setCosts(cost);
    expected.setClosingDate(closingDate);
    expected.setPaymentDueDate(paymentDueDate);

    // Ejecución
    LineOfCredit actual = mapper.toDto(lineOfCreditD);

    Assertions.assertEquals(expected, actual);
  }

  /**
   * Cuando se envia un elemento valido al metodo toEntity debe retornar un Entity con los mismos datos en los campos
   * amount, customerId, available, cost, closingDate y paymentDueDate.
   */
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
    LineOfCredit lineOfCredit = new LineOfCredit();
    lineOfCredit.setAmount(amount);
    lineOfCredit.setCustomerId(customerId);
    lineOfCredit.setAvailable(available);
    lineOfCredit.setCosts(cost);
    lineOfCredit.setClosingDate(closingDate);
    lineOfCredit.setPaymentDueDate(paymentDueDate);

    // Resultado esperado
    LineOfCreditD expected = new LineOfCreditD();
    expected.setAmount(amount);
    expected.setCustomerId(customerId);
    expected.setAvailable(available);
    expected.setCosts(cost);
    expected.setClosingDate(closingDate);
    expected.setPaymentDueDate(paymentDueDate);

    // Ejecución
    LineOfCreditD actual = mapper.toEntity(lineOfCredit);

    Assertions.assertEquals(expected, actual);
  }
}