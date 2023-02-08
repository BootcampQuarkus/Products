package com.quarkus.bootcamp.nttdata.application;

import com.quarkus.bootcamp.nttdata.domain.entity.LineOfCredit;
import com.quarkus.bootcamp.nttdata.domain.services.LineOfCreditService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/linesofcredit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LineOfCreditResource {
  @Inject
  LineOfCreditService service;

  @GET
  public Response getAll() {
    return Response.ok(service.getAll()).build();
  }

  @GET
  @Path("/{id}")
  public Response getById(@PathParam("id") Long id) {
    return Response.ok(service.getById(id)).build();
  }

  @POST
  @Transactional
  public Response create(LineOfCredit lineofCredit) {
    return Response.ok(service.create(lineofCredit)).build();
  }

  @PUT
  @Path("/{id}")
  @Transactional
  public Response update(@PathParam("id") Long id, LineOfCredit lineofCredit) {
    return Response.ok(service.update(id, lineofCredit)).build();
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  public Response delete(@PathParam("id") Long id) {
    return Response.ok(service.delete(id)).build();
  }
}
