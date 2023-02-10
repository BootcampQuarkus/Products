package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.entity.Credit;
import com.quarkus.bootcamp.nttdata.domain.mapper.CreditMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.CreditD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.CreditRepository;
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
class CreditServiceTest {
  @Inject
  CreditService service;
  @InjectMock
  CreditRepository repository;
  @Inject
  CreditMapper mapper;

  /**
   * Cuando no se tiene elementos en la BD el metodo getAll debe retornar una lista vacia.
   */
  @Test
  void getAllEmpty() {
    List<CreditD> creditDList = new ArrayList<>();
    Mockito.when(repository.getAll()).thenReturn(creditDList);
    List<Credit> actual = service.getAll();
    Assertions.assertTrue(actual.isEmpty());
  }

  /**
   * El metodo getAll debe retornar una lista.
   */
  @Test
  void getAllReturnListCredit() {
    List<CreditD> creditDList = new ArrayList<>();
    Mockito.when(repository.getAll()).thenReturn(creditDList);
    Assertions.assertInstanceOf(List.class, service.getAll());
  }

  /**
   * Cuando hay elementos en la BD el metodo getAll no retorna una lista vacia.
   */
  @Test
  void getAllValid() {
    List<CreditD> list = new ArrayList<CreditD>();
    list.add(new CreditD());
    list.add(new CreditD());
    list.add(new CreditD());
    Mockito.when(repository.getAll()).thenReturn(list);
    List<Credit> actual = service.getAll();
    Assertions.assertTrue(!actual.isEmpty());
  }

  /**
   * Cuando hay elementos en la BD el metodo getAll retorna una lista con tantos elementos como
   * elementos validos (no eliminados) hay en la bd.
   */
  @Test
  void getAllValidCount() {
    List<CreditD> list = new ArrayList<CreditD>();
    list.add(new CreditD());
    list.add(new CreditD());
    list.add(new CreditD());
    Mockito.when(repository.getAll()).thenReturn(list);
    List<Credit> actual = service.getAll();
    Long expected = 3L;
    Assertions.assertEquals(expected, actual.stream().count());
  }

  /**
   * Cuando hay elementos en la BD que han sido eliminados (softdelete) estos no se deben
   * retornar en la lista.
   */
  @Test
  void getAllValidNotDelete() {
    List<CreditD> list = new ArrayList<CreditD>();
    // Objetos validos
    list.add(new CreditD());
    list.add(new CreditD());

    // Objeto no valido
    CreditD creditD = new CreditD();
    creditD.setDeletedAt("2023.01.01");
    list.add(creditD);

    Mockito.when(repository.getAll()).thenReturn(list);
    List<Credit> actual = service.getAll();
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
  @Test
  void getByIdReturnCredit() {
    Long id = 101L;
    Mockito.when(repository.findByIdOptional(id)).thenReturn(Optional.of(new CreditD()));
    Assertions.assertInstanceOf(Credit.class, service.getById(id));
  }

  /**
   * Cuando se encuentra el elemento en la BD se debe retornar el Dto con los valores guardados
   * en la BD.
   */
  @Test
  void getByIdValid() {
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

    Mockito.when(repository.findByIdOptional(customerId)).thenReturn(Optional.of(creditD));
    Credit actual = service.getById(customerId);
    Assertions.assertEquals(expected, actual);
  }

  /**
   * Cuando se encuentra el elemento en la BD, pero este se encuentra eliminado (softdelete) se
   * debe retornar un NotFoundException.
   */
  @Test
  void getByIdDelete() {
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
    creditD.setDeletedAt("2023.01.01");

    // Resultado esperado
    Credit expected = new Credit();
    expected.setAmount(amount);
    expected.setCutomerId(customerId);
    expected.setBalance(balance);
    expected.setDues(due);
    expected.setPaymentDueDate(paymentDueDate);

    Mockito.when(repository.findByIdOptional(customerId)).thenReturn(Optional.of(creditD));
    Assertions.assertThrows(NotFoundException.class, () -> service.getById(customerId));
  }

  /**
   * El metodo create debe retornar un Dto.
   */
  @Test
  void createReturnCredit() {
    Mockito.when(repository.save(mapper.toDto(new Credit()))).thenReturn(new CreditD());
    Assertions.assertInstanceOf(Credit.class, service.create(new Credit()));
  }

  /**
   * Cuando se envia un Dto para guardar, el metodo create retorna el Dto guardado.
   */
  @Test
  void createValid() {
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
    Credit expected = credit;

    // Resultado esperado
    CreditD creditD = new CreditD();
    creditD.setAmount(amount);
    creditD.setCutomerId(customerId);
    creditD.setBalance(balance);
    creditD.setDues(due);
    creditD.setPaymentDueDate(paymentDueDate);

    Mockito.when(repository.save(mapper.toDto(credit))).thenReturn(creditD);
    Credit actual = service.create(credit);
    Assertions.assertEquals(expected, actual);
  }

  /**
   * EL metodo update retorna un Dto.
   */
  @Test
  void updateReturnCredit() {
    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.of(new CreditD()));
    Mockito.when(repository.save(new CreditD())).thenReturn(new CreditD());
    Assertions.assertInstanceOf(Credit.class, service.update(101L, new Credit()));
  }

  /**
   * Cuando se envía un Dto para actualizar y se encuentra en la BD, el metodo update
   * retorna el Dto actualizado.
   */
  @Test
  void updateValid() {
    // Variables
    Double amount = 500.00;
    Long customerId = 101L;
    Double balance = 300.00;
    Integer due = 5;
    String paymentDueDate = "10";

    Double amountNew = 600.00;
    Long customerIdNew = 102L;
    Double balanceNew = 400.00;
    Integer dueNew = 6;
    String paymentDueDateNew = "15";

    // Input
    Credit credit = new Credit();
    credit.setAmount(amountNew);
    credit.setCutomerId(customerIdNew);
    credit.setBalance(balanceNew);
    credit.setDues(dueNew);
    credit.setPaymentDueDate(paymentDueDateNew);
    Credit expected = credit;

    // Resultado esperado
    CreditD creditD = new CreditD();
    creditD.setAmount(amount);
    creditD.setCutomerId(customerId);
    creditD.setBalance(balance);
    creditD.setDues(due);
    creditD.setPaymentDueDate(paymentDueDate);
    creditD.setCreatedAt("2023.01.01");
    // --
    CreditD creditDNew = new CreditD();
    creditDNew.setAmount(amountNew);
    creditDNew.setCutomerId(customerIdNew);
    creditDNew.setBalance(balanceNew);
    creditDNew.setDues(dueNew);
    creditDNew.setPaymentDueDate(paymentDueDateNew);
    creditDNew.setCreatedAt("2023.01.01");

    Mockito.when(repository.findByIdOptional(customerId)).thenReturn(Optional.of(creditD));
    Mockito.when(repository.save(creditDNew)).thenReturn(creditDNew);

    Credit actual = service.update(customerId, credit);
    Assertions.assertEquals(expected, actual);
  }

  /**
   * Cuando se envía un Dto para actualizar y si no se encuentra en la BD, el metodo update
   * retorna un NotFoundException.
   */
  @Test
  void updateNotFound() {
    // Variables
    Long customerId = 102L;

    Mockito.when(repository.findByIdOptional(customerId)).thenThrow(new NotFoundException());
    Assertions.assertThrows(NotFoundException.class, () -> service.update(customerId, new Credit()));

  }

  /**
   * El metodo delete retorna un Entity.
   */
  @Test
  void deleteReturnCredit() {
    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.of(new CreditD()));
    Mockito.when(repository.softDelete(new CreditD())).thenReturn(new CreditD());
    Assertions.assertInstanceOf(Credit.class, service.delete(101L));
  }

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
    CreditD creditD = new CreditD();
    creditD.setDeletedAt("2023.10.10");

    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.of(creditD));
    Assertions.assertThrows(NotFoundException.class, () -> service.delete(101L));
  }

  /**
   * Cuando se envía un Id valido, el metodo delete debe retornar el Entity eliminado.
   */
  @Test
  void deleteValid() {
    // Variables
    Double amount = 500.00;
    Long customerId = 101L;
    Double balance = 300.00;
    Integer due = 5;
    String paymentDueDate = "10";

    // Input
    Credit expected = new Credit();
    expected.setAmount(amount);
    expected.setCutomerId(customerId);
    expected.setBalance(balance);
    expected.setDues(due);
    expected.setPaymentDueDate(paymentDueDate);

    // Resultado esperado
    CreditD creditD = new CreditD();
    creditD.setAmount(amount);
    creditD.setCutomerId(customerId);
    creditD.setBalance(balance);
    creditD.setDues(due);
    creditD.setPaymentDueDate(paymentDueDate);

    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.of(creditD));
    Mockito.when(repository.softDelete(creditD)).thenReturn(creditD);

    Credit actual = service.delete(101L);
    Assertions.assertEquals(expected, actual);
  }
}