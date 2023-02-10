package com.quarkus.bootcamp.nttdata.application;

import com.quarkus.bootcamp.nttdata.domain.entity.LineOfCredit;
import com.quarkus.bootcamp.nttdata.domain.services.LineOfCreditService;
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
class LineOfCreditResourceTest {
  @InjectMock
  LineOfCreditService service;

  @Test
  void getAllValid() {
    List<LineOfCredit> list = new ArrayList<LineOfCredit>();
    list.add(new LineOfCredit());
    list.add(new LineOfCredit());
    list.add(new LineOfCredit());
    String json = "[{\"amount\":null,\"cutomerId\":null,\"available\":null,\"costs\":null,\"closingDate\":null,\"paymentDueDate\":null}," +
          "{\"amount\":null,\"cutomerId\":null,\"available\":null,\"costs\":null,\"closingDate\":null,\"paymentDueDate\":null}," +
          "{\"amount\":null,\"cutomerId\":null,\"available\":null,\"costs\":null,\"closingDate\":null,\"paymentDueDate\":null}]";
    Mockito.when(service.getAll()).thenReturn(list);
    given()
          .when().get("/linesofcredit")
          .then()
          .statusCode(200)
          .body(is(json));
  }

  @Test
  void getAllEmpty() {
    List<LineOfCredit> list = new ArrayList<LineOfCredit>();
    String json = "[]";
    Mockito.when(service.getAll()).thenReturn(list);
    given()
          .when().get("/linesofcredit")
          .then()
          .statusCode(200)
          .body(is(json));
  }

  @Test
  void getByIdValid() {
    String json = "{\"amount\":null,\"cutomerId\":null,\"available\":null,\"costs\":null,\"closingDate\":null,\"paymentDueDate\":null}";
    Mockito.when(service.getById(101L)).thenReturn(new LineOfCredit());
    given()
          .when().get("/linesofcredit/101")
          .then()
          .statusCode(200)
          .body(is(json));
  }

  @Test
  void getByIdEmpty() {
    Mockito.when(service.getById(101L)).thenThrow(new NotFoundException());
    given()
          .when().get("/linesofcredit/101")
          .then()
          .statusCode(404);
  }

  @Test
  void create() {
    String bodyRequest = "{\"amount\":500.00,\"cutomerId\":101,\"available\":300.00,\"costs\":200.00,\"closingDate\":\"10\",\"paymentDueDate\":\"15\"}";
    LineOfCredit lineOfCredit = new LineOfCredit();
    lineOfCredit.setAmount(500.00);
    lineOfCredit.setCutomerId(101L);
    lineOfCredit.setAvailable(300.00);
    lineOfCredit.setCosts(200.00);
    lineOfCredit.setClosingDate("10");
    lineOfCredit.setPaymentDueDate("15");
    Mockito.when(service.create(lineOfCredit)).thenReturn(lineOfCredit);

    given()
          .body(bodyRequest)
          .header("Content-Type", "application/json")
          .when()
          .post("/linesofcredit")
          .then()
          .statusCode(201)
          .body("amount", is(500.00F),
                "cutomerId", is(101),
                "available", is(300.00F),
                "costs", is(200.00F),
                "closingDate", is("10"),
                "paymentDueDate", is("15"));
  }

  @Test
  void update() {
    String bodyRequest = "{\"amount\":500.00,\"cutomerId\":101,\"available\":300.00,\"costs\":200.00,\"closingDate\":\"10\",\"paymentDueDate\":\"15\"}";
    LineOfCredit lineOfCredit = new LineOfCredit();
    lineOfCredit.setAmount(500.00);
    lineOfCredit.setCutomerId(101L);
    lineOfCredit.setAvailable(300.00);
    lineOfCredit.setCosts(200.00);
    lineOfCredit.setClosingDate("10");
    lineOfCredit.setPaymentDueDate("15");
    Mockito.when(service.update(101L, lineOfCredit)).thenReturn(lineOfCredit);

    given()
          .body(bodyRequest)
          .header("Content-Type", "application/json")
          .when()
          .put("/linesofcredit/101")
          .then()
          .statusCode(201)
          .body("amount", is(500.00F),
                "cutomerId", is(101),
                "available", is(300.00F),
                "costs", is(200.00F),
                "closingDate", is("10"),
                "paymentDueDate", is("15"));
  }

  @Test
  void delete() {
    String bodyRequest = "{\"amount\":500.00,\"cutomerId\":101,\"available\":300.00,\"costs\":200.00,\"closingDate\":\"10\",\"paymentDueDate\":\"15\"}";
    LineOfCredit lineOfCredit = new LineOfCredit();
    lineOfCredit.setAmount(500.00);
    lineOfCredit.setCutomerId(101L);
    lineOfCredit.setAvailable(300.00);
    lineOfCredit.setCosts(200.00);
    lineOfCredit.setClosingDate("10");
    lineOfCredit.setPaymentDueDate("15");
    Mockito.when(service.delete(101L)).thenReturn(lineOfCredit);

    given()
          .body(bodyRequest)
          .header("Content-Type", "application/json")
          .when()
          .delete("/linesofcredit/101")
          .then()
          .statusCode(200)
          .body("amount", is(500.00F),
                "cutomerId", is(101),
                "available", is(300.00F),
                "costs", is(200.00F),
                "closingDate", is("10"),
                "paymentDueDate", is("15"));
  }
}