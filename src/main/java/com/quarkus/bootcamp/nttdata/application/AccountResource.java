package com.quarkus.bootcamp.nttdata.application;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.AccountNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.CustomerNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.entity.Account;
import com.quarkus.bootcamp.nttdata.domain.services.AccountService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {
  @Inject
  AccountService service;

  @GET
  public Response getAll(@QueryParam("customerId") Long customerId) {
    return ( customerId != null )?
          Response.ok(service.getAll(customerId)).build():
          Response.ok(service.getAll()).build();
  }

  @GET
  @Path("/{id}")
  public Response getById(@PathParam("id") Long id) {
    try {
      return Response.ok(service.getById(id)).build();
    } catch (AccountNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  @POST
  @Transactional
  public Response create(Account account) {
    try {
      return Response.ok(service.create(account)).status(201).build();
    } catch (CustomerNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  @PUT
  @Path("{id}")
  @Transactional
  public Response update(@PathParam("id") Long id, Account account) {
    try {
      return Response.ok(service.update(id, account)).status(201).build();
    } catch (CustomerNotFoundException | AccountNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  public Response delete(@PathParam("id") Long id) {
    try {
      return Response.ok(service.delete(id)).build();
    } catch (AccountNotFoundException e) {
      return Response.ok(e.getMessage()).status(404).build();
    }
  }
}
