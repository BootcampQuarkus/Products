package com.quarkus.bootcamp.nttdata.application;

import com.quarkus.bootcamp.nttdata.domain.entity.Credit;
import com.quarkus.bootcamp.nttdata.domain.services.CreditService;
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
class CreditResourceTest {
  @InjectMock
  CreditService service;

  @Test
  void getAllValid() {
    List<Credit> list = new ArrayList<Credit>();
    list.add(new Credit());
    list.add(new Credit());
    list.add(new Credit());
    String json = "[{\"amount\":null,\"cutomerId\":null,\"balance\":null,\"dues\":null,\"paymentDueDate\":null}," +
          "{\"amount\":null,\"cutomerId\":null,\"balance\":null,\"dues\":null,\"paymentDueDate\":null}," +
          "{\"amount\":null,\"cutomerId\":null,\"balance\":null,\"dues\":null,\"paymentDueDate\":null}]";
    Mockito.when(service.getAll()).thenReturn(list);
    given()
          .when().get("/credits")
          .then()
          .statusCode(200)
          .body(is(json));
  }

  @Test
  void getAllEmpty() {
    List<Credit> list = new ArrayList<Credit>();
    String json = "[]";
    Mockito.when(service.getAll()).thenReturn(list);
    given()
          .when().get("/credits")
          .then()
          .statusCode(200)
          .body(is(json));
  }

  @Test
  void getByIdValid() {
    String json = "{\"amount\":null,\"cutomerId\":null,\"balance\":null,\"dues\":null,\"paymentDueDate\":null}";
    Mockito.when(service.getById(101L)).thenReturn(new Credit());
    given()
          .when().get("/credits/101")
          .then()
          .statusCode(200)
          .body(is(json));
  }

  @Test
  void getByIdEmpty() {
    Mockito.when(service.getById(101L)).thenThrow(new NotFoundException());
    given()
          .when().get("/credits/101")
          .then()
          .statusCode(404);
  }

  @Test
  void create() {
    String bodyRequest = "{\"amount\":500.00,\"cutomerId\":101,\"balance\":300.00,\"dues\":5,\"paymentDueDate\":\"15\"}";
    Credit credit = new Credit();
    credit.setAmount(500.00);
    credit.setCutomerId(101L);
    credit.setBalance(300.00);
    credit.setDues(5);
    credit.setPaymentDueDate("15");
    Mockito.when(service.create(credit)).thenReturn(credit);

    given()
          .body(bodyRequest)
          .header("Content-Type", "application/json")
          .when()
          .post("/credits")
          .then()
          .statusCode(201)
          .body("amount", is(500.00F),
                "cutomerId", is(101),
                "balance", is(300.00F),
                "dues", is(5),
                "paymentDueDate", is("15"));
  }

  @Test
  void update() {
    String bodyRequest = "{\"amount\":500.00,\"cutomerId\":101,\"balance\":300.00,\"dues\":5,\"paymentDueDate\":\"15\"}";
    Credit credit = new Credit();
    credit.setAmount(500.00);
    credit.setCutomerId(101L);
    credit.setBalance(300.00);
    credit.setDues(5);
    credit.setPaymentDueDate("15");
    Mockito.when(service.update(101L, credit)).thenReturn(credit);

    given()
          .body(bodyRequest)
          .header("Content-Type", "application/json")
          .when()
          .put("/credits/101")
          .then()
          .statusCode(201)
          .body("amount", is(500.00F),
                "cutomerId", is(101),
                "balance", is(300.00F),
                "dues", is(5),
                "paymentDueDate", is("15"));
  }

  @Test
  void delete() {
    String bodyRequest = "{\"amount\":500.00,\"cutomerId\":101,\"balance\":300.00,\"dues\":5,\"paymentDueDate\":\"15\"}";
    Credit credit = new Credit();
    credit.setAmount(500.00);
    credit.setCutomerId(101L);
    credit.setBalance(300.00);
    credit.setDues(5);
    credit.setPaymentDueDate("15");
    Mockito.when(service.delete(101L)).thenReturn(credit);

    given()
          .body(bodyRequest)
          .header("Content-Type", "application/json")
          .when()
          .delete("/credits/101")
          .then()
          .statusCode(200)
          .body("amount", is(500.00F),
                "cutomerId", is(101),
                "balance", is(300.00F),
                "dues", is(5),
                "paymentDueDate", is("15"));
  }
}