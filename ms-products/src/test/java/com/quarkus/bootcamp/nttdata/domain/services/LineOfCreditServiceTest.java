package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.entity.LineOfCredit;
import com.quarkus.bootcamp.nttdata.domain.mapper.LineOfCreditMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.LineOfCreditD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.LineOfCreditRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@QuarkusTest
class LineOfCreditServiceTest {
  @Inject
  LineOfCreditService service;
  @InjectMock
  LineOfCreditRepository repository;
  @Inject
  LineOfCreditMapper mapper;

  @Test
  void getAllEmpty() {
    List<LineOfCreditD> lineOfCreditD = new ArrayList<>();
    Mockito.when(repository.getAll()).thenReturn(lineOfCreditD);
    List<LineOfCredit> actual = service.getAll();
    Assertions.assertTrue(actual.isEmpty());
  }

  @Test
  void getAllReturnListLineOfCredit() {
    List<LineOfCreditD> lineOfCreditD = new ArrayList<>();
    Mockito.when(repository.getAll()).thenReturn(lineOfCreditD);
    List<LineOfCredit> actual = service.getAll();
    Assertions.assertInstanceOf(List.class, actual);
  }

  @Test
  void getAllValid() {
    List<LineOfCreditD> list = new ArrayList<LineOfCreditD>();
    list.add(new LineOfCreditD());
    list.add(new LineOfCreditD());
    list.add(new LineOfCreditD());
    Mockito.when(repository.getAll()).thenReturn(list);
    List<LineOfCredit> actual = service.getAll();
    Assertions.assertTrue(!actual.isEmpty());
  }

  @Test
  void getAllValidNotDelete() {
    List<LineOfCreditD> list = new ArrayList<LineOfCreditD>();
    // Objetos validos
    list.add(new LineOfCreditD());
    list.add(new LineOfCreditD());

    // Objeto no valido
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    lineOfCreditD.setDeletedAt("2023.01.01");
    list.add(lineOfCreditD);

    Mockito.when(repository.getAll()).thenReturn(list);
    List<LineOfCredit> actual = service.getAll();
    Long expected = 2L;
    Assertions.assertEquals(expected, actual.stream().count());
  }

  @Test
  void getByIdEmpty() {
    Long id = 101L;
    Mockito.when(repository.findByIdOptional(id)).thenReturn(Optional.empty());
    Assertions.assertThrows(NotFoundException.class, () -> service.getById(id));
  }

  @Test
  void getByIdReturnLineOfCredit() {
    Long id = 101L;
    Mockito.when(repository.findByIdOptional(id)).thenReturn(Optional.of(new LineOfCreditD()));
    LineOfCredit actual = service.getById(id);
    Assertions.assertInstanceOf(LineOfCredit.class, actual);
  }

  @Test
  void getByIdValid() {
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

    Mockito.when(repository.findByIdOptional(customerId)).thenReturn(Optional.of(lineOfCreditD));
    LineOfCredit actual = service.getById(customerId);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void getByIdDelete() {
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
    lineOfCreditD.setDeletedAt("2023.01.01");

    // Resultado esperado
    LineOfCredit expected = new LineOfCredit();
    expected.setAmount(amount);
    expected.setCutomerId(customerId);
    expected.setAvailable(available);
    expected.setCosts(cost);
    expected.setClosingDate(closingDate);
    expected.setPaymentDueDate(paymentDueDate);

    Mockito.when(repository.findByIdOptional(customerId)).thenReturn(Optional.of(lineOfCreditD));
    Assertions.assertThrows(NotFoundException.class, () -> service.getById(customerId));
  }

  @Test
  void createReturnAccount() {
    Mockito.when(repository.save(mapper.toDto(new LineOfCredit()))).thenReturn(new LineOfCreditD());
    LineOfCredit actual = service.create(new LineOfCredit());
    Assertions.assertInstanceOf(LineOfCredit.class, actual);
  }

  @Test
  void createValid() {
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
    LineOfCredit expected = lineOfCredit;

    // Resultado esperado
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    lineOfCreditD.setAmount(amount);
    lineOfCreditD.setCutomerId(customerId);
    lineOfCreditD.setAvailable(available);
    lineOfCreditD.setCosts(cost);
    lineOfCreditD.setClosingDate(closingDate);
    lineOfCreditD.setPaymentDueDate(paymentDueDate);
    lineOfCreditD.setCreatedAt("2023.01.01");

    Mockito.when(repository.save(mapper.toDto(lineOfCredit))).thenReturn(lineOfCreditD);
    LineOfCredit actual = service.create(lineOfCredit);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void updateReturnAccount() {
    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.of(new LineOfCreditD()));
    Mockito.when(repository.save(new LineOfCreditD())).thenReturn(new LineOfCreditD());

    LineOfCredit actual = service.update(101L, new LineOfCredit());
    Assertions.assertInstanceOf(LineOfCredit.class, actual);
  }

  @Test
  void updateValid() {
    // Variables
    Double amount = 500.00;
    Long customerId = 101L;
    Double available = 300.00;
    Double cost = 200.00;
    String closingDate = "10";
    String paymentDueDate = "10";

    Double amountNew = 600.00;
    Long customerIdNew = 102L;
    Double availableNew = 350.00;
    Double costNew = 250.00;
    String closingDateNew = "12";
    String paymentDueDateNew = "15";

    // Input
    LineOfCredit lineOfCredit = new LineOfCredit();
    lineOfCredit.setAmount(amountNew);
    lineOfCredit.setCutomerId(customerIdNew);
    lineOfCredit.setAvailable(availableNew);
    lineOfCredit.setCosts(costNew);
    lineOfCredit.setClosingDate(closingDateNew);
    lineOfCredit.setPaymentDueDate(paymentDueDateNew);
    LineOfCredit expected = lineOfCredit;

    // Resultado esperado
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    lineOfCreditD.setAmount(amount);
    lineOfCreditD.setCutomerId(customerId);
    lineOfCreditD.setAvailable(available);
    lineOfCreditD.setCosts(cost);
    lineOfCreditD.setClosingDate(closingDate);
    lineOfCreditD.setPaymentDueDate(paymentDueDate);
    lineOfCreditD.setCreatedAt("2023.01.01");
    // --
    LineOfCreditD lineOfCreditDNew = new LineOfCreditD();
    lineOfCreditDNew.setAmount(amountNew);
    lineOfCreditDNew.setCutomerId(customerIdNew);
    lineOfCreditDNew.setAvailable(availableNew);
    lineOfCreditDNew.setCosts(costNew);
    lineOfCreditDNew.setClosingDate(closingDateNew);
    lineOfCreditDNew.setPaymentDueDate(paymentDueDateNew);
    lineOfCreditDNew.setCreatedAt("2023.01.01");

    Mockito.when(repository.findByIdOptional(customerId)).thenReturn(Optional.of(lineOfCreditD));
    Mockito.when(repository.save(lineOfCreditDNew)).thenReturn(lineOfCreditDNew);

    LineOfCredit actual = service.update(customerId, lineOfCredit);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void delete() {
  }
}