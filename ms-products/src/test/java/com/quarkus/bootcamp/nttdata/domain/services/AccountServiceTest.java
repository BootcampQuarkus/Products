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

  @Test
  void getAllEmpty() {
    List<AccountD> accountList = new ArrayList<>();
    Mockito.when(repository.getAll()).thenReturn(accountList);
    List<Account> actual = service.getAll();
    Assertions.assertTrue(actual.isEmpty());
  }

  @Test
  void getAllReturnListAccount() {
    List<AccountD> accountList = new ArrayList<>();
    Mockito.when(repository.getAll()).thenReturn(accountList);
    List<Account> actual = service.getAll();
    Assertions.assertInstanceOf(List.class, actual);
  }

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

  @Test
  void getByIdEmpty() {
    Long id = 101L;
    Mockito.when(repository.findByIdOptional(id)).thenReturn(Optional.empty());
    Assertions.assertThrows(NotFoundException.class, () -> service.getById(id));
  }

  @Test
  void getByIdReturnAccount() {
    Long id = 101L;
    Mockito.when(repository.findByIdOptional(id)).thenReturn(Optional.of(new AccountD()));
    Account actual = service.getById(id);
    Assertions.assertInstanceOf(Account.class, actual);
  }

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

  @Test
  void getByIdDelete() {
    Long id = 101L;
    Double amount = 500.00;
    Long cardId = 101L;

    // Input
    AccountD accountD = new AccountD();
    accountD.setAmount(amount);
    accountD.setCutomerId(id);
    accountD.setCardId(cardId);
    accountD.setDeletedAt("2023.01.01");

    Mockito.when(repository.findByIdOptional(id)).thenReturn(Optional.of(accountD));
    Assertions.assertThrows(NotFoundException.class, () -> service.getById(id));
  }

  @Test
  void createReturnAccount() {
    Mockito.when(repository.save(mapper.toDto(new Account()))).thenReturn(new AccountD());
    Account actual = service.create(new Account());
    Assertions.assertInstanceOf(Account.class, actual);
  }

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

  @Test
  void updateReturnAccount() {
    Mockito.when(repository.findByIdOptional(101L)).thenReturn(Optional.of(new AccountD()));
    Mockito.when(repository.save(new AccountD())).thenReturn(new AccountD());

    Account actual = service.update(101L, new Account());
    Assertions.assertInstanceOf(Account.class, actual);
  }

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

  @Test
  void delete() {
    Assertions.assertEquals(true, false);
  }
}