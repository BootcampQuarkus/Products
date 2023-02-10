package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.entity.Account;
import com.quarkus.bootcamp.nttdata.domain.mapper.AccountMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.AccountD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.AccountRepository;
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
class AccountServiceTest {
  @Inject
  AccountService service;
  @InjectMock
  AccountRepository repository;
  @Inject
  AccountMapper mapper;

  /**
   * Cuando no se tiene elementos en la BD el metodo getAll debe retornar una lista vacia.
   */
  @Test
  void getAllEmpty() {
    List<AccountD> accountList = new ArrayList<>();
    Mockito.when(repository.getAll()).thenReturn(accountList);
    List<Account> actual = service.getAll();
    Assertions.assertTrue(actual.isEmpty());
  }

  /**
   * El metodo getAll debe retornar una lista.
   */
  @Test
  void getAllReturnListAccount() {
    List<AccountD> accountList = new ArrayList<>();
    Mockito.when(repository.getAll()).thenReturn(accountList);
    Assertions.assertInstanceOf(List.class, service.getAll());
  }

  /**
   * Cuando hay elementos en la BD el metodo getAll no retorna una lista vacia.
   */
  @Test
  void getAllValid() {
    List<AccountD> list = new ArrayList<AccountD>();
    list.add(new AccountD());
    list.add(new AccountD());
    list.add(new AccountD());
    Mockito.when(repository.getAll()).thenReturn(list);
    List<Account> actual = service.getAll();
    Assertions.assertTrue(!actual.isEmpty());
  }

  /**
   * Cuando hay elementos en la BD el metodo getAll retorna una lista con tantos elementos como
   * elementos validos (no eliminados) hay en la bd.
   */
  @Test
  void getAllValidCount() {
    List<AccountD> list = new ArrayList<AccountD>();
    list.add(new AccountD());
    list.add(new AccountD());
    list.add(new AccountD());
    Mockito.when(repository.getAll()).thenReturn(list);
    List<Account> actual = service.getAll();
    Long expected = 3L;
    Assertions.assertEquals(expected, actual.stream().count());
  }

  /**
   * Cuando hay elementos en la BD que han sido eliminados (softdelete) estos no se deben
   * retornar en la lista.
   */
  @Test
  void getAllValidNotDelete() {
    List<AccountD> list = new ArrayList<AccountD>();
    // Objetos validos
    list.add(new AccountD());
    list.add(new AccountD());

    // Objeto no valido
    AccountD accountD = new AccountD();
    accountD.setDeletedAt("2023.01.01");
    list.add(accountD);

    Mockito.when(repository.getAll()).thenReturn(list);
    List<Account> actual = service.getAll();
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
  void getByIdReturnAccount() {
    Long id = 101L;
    Mockito.when(repository.findByIdOptional(id)).thenReturn(Optional.of(new AccountD()));
    Assertions.assertInstanceOf(Account.class, service.getById(id));
  }

  /**
   * Cuando se encuentra el elemento en la BD se debe retornar el Dto con los valores guardados
   * en la BD.
   */
  @Test
  void getByIdValid() {
    Long id = 101L;
    Double amount = 500.00;
    Long cardId = 101L;

    // Input
    AccountD accountD = new AccountD();
    accountD.setAmount(amount);
    accountD.setCutomerId(id);
    accountD.setCardId(cardId);

    // Resultado esperado
    Account expected = new Account();
    expected.setAmount(amount);
    expected.setCutomerId(id);
    expected.setCardId(cardId);

    Mockito.when(repository.findByIdOptional(id)).thenReturn(Optional.of(accountD));
    Account actual = service.getById(id);
    Assertions.assertEquals(expected, actual);
  }

  /**
   * Cuando se encuentra el elemento en la BD, pero este se encuentra eliminado (softdelete) se
   * debe retornar un NotFoundException.
   */
  @Test
  void getByIdDelete() {
    Long id = 101L;

    // Input
    AccountD accountD = new AccountD();
    accountD.setDeletedAt("2023.01.01");

    Mockito.when(repository.findByIdOptional(id)).thenReturn(Optional.of(accountD));
    Assertions.assertThrows(NotFoundException.class, () -> service.getById(id));
  }

  /**
   * El metodo create debe retornar un Dto.
   */
  @Test
  void createReturnAccount() {
    Mockito.when(repository.save(mapper.toDto(new Account()))).thenReturn(new AccountD());
    Assertions.assertInstanceOf(Account.class, service.create(new Account()));
  }

  /**
   * Cuando se envia un Dto para guardar, el metodo create retorna el Dto guardado.
   */
  @Test
  void createValid() {
    // Variables
    Double amount = 500.00;
    Long customerId = 101L;
    Long cardId = 101L;

    // Input
    Account account = new Account();
    account.setAmount(amount);
    account.setCutomerId(customerId);
    account.setCardId(cardId);
    Account expected = account;

    // Resultado esperado
    AccountD accountD = new AccountD();
    accountD.setAmount(amount);
    accountD.setCutomerId(customerId);
    accountD.setCardId(cardId);
    accountD.setCreatedAt("2023.01.01");

    Mockito.when(repository.save(mapper.toDto(account))).thenReturn(accountD);
    Account actual = service.create(account);
    Assertions.assertEquals(expected, actual);
  }

  /**
   * EL metodo update retorna un Dto.
   */
  @Test
  void updateReturnAccount() {
    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.of(new AccountD()));
    Mockito.when(repository.save(new AccountD())).thenReturn(new AccountD());
    Assertions.assertInstanceOf(Account.class, service.update(101L, new Account()));
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
    Long cardId = 101L;

    Double amountNew = 600.00;
    Long customerIdNew = 102L;
    Long cardIdNew = 102L;

    // Input
    Account account = new Account();
    account.setAmount(amountNew);
    account.setCutomerId(customerIdNew);
    account.setCardId(cardIdNew);
    Account expected = account;

    // Resultado esperado
    AccountD accountD = new AccountD();
    accountD.setAmount(amount);
    accountD.setCutomerId(customerId);
    accountD.setCardId(cardId);
    accountD.setCreatedAt("2023.01.01");
    // --
    AccountD accountDNew = new AccountD();
    accountDNew.setAmount(amountNew);
    accountDNew.setCutomerId(customerIdNew);
    accountDNew.setCardId(cardIdNew);
    accountDNew.setCreatedAt("2023.01.01");

    Mockito.when(repository.findByIdOptional(customerId)).thenReturn(Optional.of(accountD));
    Mockito.when(repository.save(accountDNew)).thenReturn(accountDNew);

    Account actual = service.update(customerId, account);
    Assertions.assertEquals(expected, actual);
  }

  /**
   * Cuando se envía un Dto para actualizar y si no se encuentra en la BD, el metodo update
   * retorna un NotFoundException.
   */
  @Test
  void updateNotFound() {
    // Variables
    Long customerId = 101L;

    Mockito.when(repository.findByIdOptional(customerId)).thenThrow(new NotFoundException());
    Assertions.assertThrows(NotFoundException.class, () -> service.update(customerId, new Account()));
  }

  /**
   * El metodo delete retorna un Entity.
   */
  @Test
  void deleteReturnAccount() {
    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.of(new AccountD()));
    Mockito.when(repository.softDelete(new AccountD())).thenReturn(new AccountD());
    Assertions.assertInstanceOf(Account.class, service.delete(101L));
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
    AccountD accountD = new AccountD();
    accountD.setDeletedAt("2023.10.10");

    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.of(accountD));
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
    Long cardId = 101L;

    Account expected = new Account();
    expected.setAmount(amount);
    expected.setCutomerId(customerId);
    expected.setCardId(cardId);

    AccountD accountD = new AccountD();
    accountD.setAmount(amount);
    accountD.setCutomerId(customerId);
    accountD.setCardId(cardId);

    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.of(accountD));
    Mockito.when(repository.softDelete(accountD)).thenReturn(accountD);

    Account actual = service.delete(101L);
    Assertions.assertEquals(expected, actual);
  }
}