package com.quarkus.bootcamp.nttdata.application;

import com.quarkus.bootcamp.nttdata.domain.entity.Account;
import com.quarkus.bootcamp.nttdata.domain.services.AccountService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class AccountResourceTest {
  @InjectMock
  AccountService service;

  @Test
  void getAllValid() {
    List<Account> list = new ArrayList<Account>();
    list.add(new Account());
    list.add(new Account());
    list.add(new Account());
    String json = "[{\"amount\":null,\"cutomerId\":null,\"cardId\":null}," +
          "{\"amount\":null,\"cutomerId\":null,\"cardId\":null}," +
          "{\"amount\":null,\"cutomerId\":null,\"cardId\":null}]";
    Mockito.when(service.getAll()).thenReturn(list);
    given()
          .when().get("/accounts")
          .then()
          .statusCode(200)
          .body(is(json));
  }

  @Test
  void getAllEmpty() {
    List<Account> list = new ArrayList<Account>();
    String json = "[]";
    Mockito.when(service.getAll()).thenReturn(list);
    given()
          .when().get("/accounts")
          .then()
          .statusCode(200)
          .body(is(json));
  }

  @Test
  void getByIdValid() {
    String json = "{\"amount\":null,\"cutomerId\":null,\"cardId\":null}";
    Mockito.when(service.getById(101L)).thenReturn(new Account());
    given()
          .when().get("/accounts/101")
          .then()
          .statusCode(200)
          .body(is(json));
  }

  @Test
  void getByIdEmpty() {
    Mockito.when(service.getById(101L)).thenThrow(new NotFoundException());
    given()
          .when().get("/accounts/101")
          .then()
          .statusCode(404);
  }

  @Test
  void create() {
    String bodyRequest = "{\"amount\":500.00,\"cutomerId\":101,\"cardId\":101}";
    Account account = new Account();
    account.setAmount(500.00);
    account.setCustomerId(101L);
    account.setCardId(101L);
    Mockito.when(service.create(account)).thenReturn(account);

    given()
          .body(bodyRequest)
          .header("Content-Type", "application/json")
          .when()
          .post("/accounts")
          .then()
          .statusCode(201)
          .body("amount", is(500.00F),
                "cutomerId", is(101),
                "cardId", is(101));
  }

  @Test
  void update() {
    String bodyRequest = "{\"amount\":500.00,\"cutomerId\":101,\"cardId\":101}";
    Account account = new Account();
    account.setAmount(500.00);
    account.setCustomerId(101L);
    account.setCardId(101L);
    Mockito.when(service.update(101L, account)).thenReturn(account);

    given()
          .body(bodyRequest)
          .header("Content-Type", "application/json")
          .when()
          .put("/accounts/101")
          .then()
          .statusCode(201)
          .body("amount", is(500.00F),
                "cutomerId", is(101),
                "cardId", is(101));
  }

  @Test
  void delete() {
    String bodyRequest = "{\"amount\":500.00,\"cutomerId\":101,\"cardId\":101}";
    Account account = new Account();
    account.setAmount(500.00);
    account.setCustomerId(101L);
    account.setCardId(101L);
    Mockito.when(service.delete(101L)).thenReturn(account);

    given()
          .body(bodyRequest)
          .header("Content-Type", "application/json")
          .when()
          .delete("/accounts/101")
          .then()
          .statusCode(200)
          .body("amount", is(500.00F),
                "cutomerId", is(101),
                "cardId", is(101));
  }
}