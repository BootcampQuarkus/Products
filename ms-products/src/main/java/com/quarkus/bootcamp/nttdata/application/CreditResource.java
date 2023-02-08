package com.quarkus.bootcamp.nttdata.application;

import com.quarkus.bootcamp.nttdata.domain.entity.Credit;
import com.quarkus.bootcamp.nttdata.domain.services.CreditService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/credits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CreditResource {

  @Inject
  CreditService service;

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
  public Response create(Credit credit) {
    return Response.ok(service.create(credit)).build();
  }

  @PUT
  @Path("{id}")
  @Transactional
  public Response update(@PathParam("id") Long id, Credit credit) {
    return Response.ok(service.update(id, credit)).build();
  }

  @DELETE
  @Path("{id}")
  @Transactional
  public Response delete(@PathParam("id") Long id) {
    return Response.ok(service.delete(id)).build();
  }
}
