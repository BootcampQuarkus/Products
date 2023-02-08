package com.quarkus.bootcamp.nttdata.infraestructure.repository;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.CreditD;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para los creditos.
 *
 * @author pdiaz
 */
@ApplicationScoped
public class CreditRepository implements IRepository<CreditD> {
  /**
   * Devuelve una lista con todos los elementos no eliminados (softdelete)
   * de la base de datos Credit.
   *
   * @return Lista de credito.
   */
  @Override
  public List<CreditD> getAll() {
    return CreditD.listAll();
  }

  /**
   * Devuelve una credito si existe.
   *
   * @param id Identificador del credito a devolver.
   * @return El credito buscado.
   */
  @Override
  public CreditD getById(Long id) {
    Optional<CreditD> creditD = CreditD.findByIdOptional(id);
    if (creditD.isEmpty()) {
      throw new NullPointerException("Credit not found");
    }
    return creditD.get();
  }

  /**
   * Guarda en la base de datos la información del credito.
   *
   * @param creditD Credito a guardar.
   * @return Credito guardada.
   */
  @Override
  public CreditD save(CreditD creditD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    String date = ZonedDateTime.now(ZoneId.systemDefault()).format(formatter);
    if (creditD.getCreatedAt() == null) {
      creditD.setCreatedAt(date);
    } else {
      creditD.setUpdatedAt(date);
    }
    creditD.persist();
    return creditD;
  }

  /**
   * Realiza el borrado lógico del credito indicado.
   *
   * @param creditD Credito a borrar logicamente.
   * @return Credito borrada.
   */
  @Override
  public CreditD softDelete(CreditD creditD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    creditD.setDeletedAt(ZonedDateTime.now(ZoneId.systemDefault()).format(formatter));
    return this.save(creditD);
  }
}
