package com.quarkus.bootcamp.nttdata.infraestructure.repository;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.AccountD;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para las cuentas (corrientes, ahorros).
 *
 * @author pdiaz
 */
@ApplicationScoped
public class AccountRepository implements IRepository<AccountD> {
  /**
   * Devuelve una lista con todos los elementos no eliminados (softdelete)
   * de la base de datos Account.
   *
   * @return Lista de cuentas.
   */
  @Override
  public List<AccountD> getAll() {
    return AccountD.listAll();
  }

  /**
   * Devuelve una cuenta si existe.
   *
   * @param id Identificador de la cuenta a devolver.
   * @return La cuenta buscada.
   */
  @Override
  public AccountD getById(Long id) {
    Optional<AccountD> accountD = AccountD.findByIdOptional(id);
    if (accountD.isEmpty()) {
      throw new NullPointerException("Account not found");
    }
    return accountD.get();
  }

  /**
   * Guarda en la base de datos la información de la cuenta.
   *
   * @param account Cuenta a guardar.
   * @return Cuenta guardada.
   */
  @Override
  public AccountD save(AccountD account) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    String date = ZonedDateTime.now(ZoneId.systemDefault()).format(formatter);
    if (account.getCreatedAt() == null) {
      account.setCreatedAt(date);
    } else {
      account.setUpdatedAt(date);
    }
    account.persist();
    return account;
  }

  /**
   * Realiza el borrado lógico de la cuenta indicada.
   *
   * @param accountD Cuenta a borrar logicamente.
   * @return Cuenta borrada.
   */
  @Override
  public AccountD softDelete(AccountD accountD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    accountD.setDeletedAt(ZonedDateTime.now(ZoneId.systemDefault()).format(formatter));
    return this.save(accountD);
  }

  public List<AccountD> findByCustomerId(Long id) {
    return AccountD.find("customerId", id)
          .list();
  }

}
