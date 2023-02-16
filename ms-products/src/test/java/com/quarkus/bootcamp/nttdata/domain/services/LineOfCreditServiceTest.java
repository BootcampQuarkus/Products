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

  /**
   * Cuando no se tiene elementos en la BD el metodo getAll debe retornar una lista vacia.
   */
  @Test
  void getAllEmpty() {
    List<LineOfCreditD> lineOfCreditD = new ArrayList<>();
    Mockito.when(repository.getAll()).thenReturn(lineOfCreditD);
    List<LineOfCredit> actual = service.getAll();
    Assertions.assertTrue(actual.isEmpty());
  }

  /**
   * El metodo getAll debe retornar una lista.
   */
  @Test
  void getAllReturnListLineOfCredit() {
    List<LineOfCreditD> lineOfCreditD = new ArrayList<>();
    Mockito.when(repository.getAll()).thenReturn(lineOfCreditD);
    Assertions.assertInstanceOf(List.class, service.getAll());
  }

  /**
   * Cuando hay elementos en la BD el metodo getAll no retorna una lista vacia.
   */
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

  /**
   * Cuando hay elementos en la BD el metodo getAll retorna una lista con tantos elementos como
   * elementos validos (no eliminados) hay en la bd.
   */
  @Test
  void getAllValidCount() {
    List<LineOfCreditD> list = new ArrayList<LineOfCreditD>();
    list.add(new LineOfCreditD());
    list.add(new LineOfCreditD());
    list.add(new LineOfCreditD());
    Mockito.when(repository.getAll()).thenReturn(list);
    List<LineOfCredit> actual = service.getAll();
    Long expected = 3L;
    Assertions.assertEquals(expected, actual.stream().count());
  }

  /**
   * Cuando hay elementos en la BD que han sido eliminados (softdelete) estos no se deben
   * retornar en la lista.
   */
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

  /**
   * Cuando no se encuentra el elemento en la BD el metodo getById debe retornar un NotFoundException.
   */
  @Test
  void getByIdEmpty() {
    Long id = 101L;
    Mockito.when(repository.findByIdOptional(id)).thenReturn(Optional.empty());
    Assertions.assertThrows(NotFoundException.class, () -> service.getById(id));
  }

  /**
   * El metodo getById debe retornar un Dto.
   */
  /*@Test
  void getByIdReturnLineOfCredit() {
    Long id = 101L;
    Mockito.when(repository.findByIdOptional(id)).thenReturn(Optional.of(new LineOfCreditD()));
    Assertions.assertInstanceOf(LineOfCredit.class, service.getById(id));
  }*/

  /**
   * Cuando se encuentra el elemento en la BD se debe retornar el Dto con los valores guardados
   * en la BD.
   */
  /*@Test
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

    Mockito.when(repository.findByIdOptional(customerId)).thenReturn(Optional.of(lineOfCreditD));
    LineOfCredit actual = service.getById(customerId);
    Assertions.assertEquals(expected, actual);
  }*/

  /**
   * Cuando se encuentra el elemento en la BD, pero este se encuentra eliminado (softdelete) se
   * debe retornar un NotFoundException.
   */
  @Test
  void getByIdDelete() {
    // Variables
    Long customerId = 101L;

    // Input
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    lineOfCreditD.setDeletedAt("2023.01.01");

    Mockito.when(repository.findByIdOptional(customerId)).thenReturn(Optional.of(lineOfCreditD));
    Assertions.assertThrows(NotFoundException.class, () -> service.getById(customerId));
  }

  /**
   * El metodo create debe retornar un Dto.
   */
  /*@Test
  void createReturnLineOfCredit() {
    Mockito.when(repository.save(mapper.toEntity(new LineOfCredit()))).thenReturn(new LineOfCreditD());
    Assertions.assertInstanceOf(LineOfCredit.class, service.create(new LineOfCredit()));
  }*/

  /**
   * Cuando se envia un Dto para guardar, el metodo create retorna el Dto guardado.
   */
  /*@Test
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
    lineOfCredit.setCustomerId(customerId);
    lineOfCredit.setAvailable(available);
    lineOfCredit.setCosts(cost);
    lineOfCredit.setClosingDate(closingDate);
    lineOfCredit.setPaymentDueDate(paymentDueDate);
    LineOfCredit expected = lineOfCredit;

    // Resultado esperado
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    lineOfCreditD.setAmount(amount);
    lineOfCreditD.setCustomerId(customerId);
    lineOfCreditD.setAvailable(available);
    lineOfCreditD.setCosts(cost);
    lineOfCreditD.setClosingDate(closingDate);
    lineOfCreditD.setPaymentDueDate(paymentDueDate);
    lineOfCreditD.setCreatedAt("2023.01.01");

    Mockito.when(repository.save(mapper.toEntity(lineOfCredit))).thenReturn(lineOfCreditD);
    LineOfCredit actual = service.create(lineOfCredit);
    Assertions.assertEquals(expected, actual);
  }*/

  /**
   * EL metodo update retorna un Dto.
   */
  /*@Test
  void updateReturnAccount() {
    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.of(new LineOfCreditD()));
    Mockito.when(repository.save(new LineOfCreditD())).thenReturn(new LineOfCreditD());
    Assertions.assertInstanceOf(LineOfCredit.class, service.update(101L, new LineOfCredit()));
  }*/

  /**
   * Cuando se envía un Dto para actualizar y se encuentra en la BD, el metodo update
   * retorna el Dto actualizado.
   */
  /*@Test
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
    lineOfCredit.setCustomerId(customerIdNew);
    lineOfCredit.setAvailable(availableNew);
    lineOfCredit.setCosts(costNew);
    lineOfCredit.setClosingDate(closingDateNew);
    lineOfCredit.setPaymentDueDate(paymentDueDateNew);
    LineOfCredit expected = lineOfCredit;

    // Resultado esperado
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    lineOfCreditD.setAmount(amount);
    lineOfCreditD.setCustomerId(customerId);
    lineOfCreditD.setAvailable(available);
    lineOfCreditD.setCosts(cost);
    lineOfCreditD.setClosingDate(closingDate);
    lineOfCreditD.setPaymentDueDate(paymentDueDate);
    lineOfCreditD.setCreatedAt("2023.01.01");
    // --
    LineOfCreditD lineOfCreditDNew = new LineOfCreditD();
    lineOfCreditDNew.setAmount(amountNew);
    lineOfCreditDNew.setCustomerId(customerIdNew);
    lineOfCreditDNew.setAvailable(availableNew);
    lineOfCreditDNew.setCosts(costNew);
    lineOfCreditDNew.setClosingDate(closingDateNew);
    lineOfCreditDNew.setPaymentDueDate(paymentDueDateNew);
    lineOfCreditDNew.setCreatedAt("2023.01.01");

    Mockito.when(repository.findByIdOptional(customerId)).thenReturn(Optional.of(lineOfCreditD));
    Mockito.when(repository.save(lineOfCreditDNew)).thenReturn(lineOfCreditDNew);

    LineOfCredit actual = service.update(customerId, lineOfCredit);
    Assertions.assertEquals(expected, actual);
  }*/

  /**
   * Cuando se envía un Dto para actualizar y si no se encuentra en la BD, el metodo update
   * retorna un NotFoundException.
   */
  @Test
  void updateNotFound() {
    // Variables
    Long id = 101L;

    Mockito.when(repository.findByIdOptional(id)).thenThrow(new NotFoundException());
    Assertions.assertThrows(NotFoundException.class, () -> service.update(id, new LineOfCredit()));
  }

  /**
   * El metodo delete retorna un Entity.
   */
  /*@Test
  void deleteReturnLineOfCredit() {
    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.of(new LineOfCreditD()));
    Mockito.when(repository.softDelete(new LineOfCreditD())).thenReturn(new LineOfCreditD());
    Assertions.assertInstanceOf(LineOfCredit.class, service.delete(101L));
  }*/

  /**
   * Cuando se envía un Id de un elemento que no existe al metodo delete, se debe retornar
   * NotFoundException.
   */
  @Test
  void deleteNotFound() {
    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.empty());
    Assertions.assertThrows(NotFoundException.class, () -> service.delete(101L));
  }

  /**
   * Cuando se envía un Id de un elemento que ya se eliminó al metodo delete, se debe retornar
   * NotFoundException.
   */
  @Test
  void deleteSoftDelete() {
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    lineOfCreditD.setDeletedAt("2023.10.10");

    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.of(lineOfCreditD));
    Assertions.assertThrows(NotFoundException.class, () -> service.delete(101L));
  }

  /**
   * Cuando se envía un Id valido, el metodo delete debe retornar el Entity eliminado.
   */
  /*@Test
  void deleteValid() {
    // Variables
    Double amount = 500.00;
    Long customerId = 101L;
    Double available = 300.00;
    Double cost = 200.00;
    String closingDate = "10";
    String paymentDueDate = "10";

    // Input
    LineOfCredit expected = new LineOfCredit();
    expected.setAmount(amount);
    expected.setCustomerId(customerId);
    expected.setAvailable(available);
    expected.setCosts(cost);
    expected.setClosingDate(closingDate);
    expected.setPaymentDueDate(paymentDueDate);

    // Resultado esperado
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    lineOfCreditD.setAmount(amount);
    lineOfCreditD.setCustomerId(customerId);
    lineOfCreditD.setAvailable(available);
    lineOfCreditD.setCosts(cost);
    lineOfCreditD.setClosingDate(closingDate);
    lineOfCreditD.setPaymentDueDate(paymentDueDate);
    lineOfCreditD.setCreatedAt("2023.01.01");

    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.of(lineOfCreditD));
    Mockito.when(repository.softDelete(lineOfCreditD)).thenReturn(lineOfCreditD);

    LineOfCredit actual = service.delete(101L);
    Assertions.assertEquals(expected, actual);
  }*/
}