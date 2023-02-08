package com.quarkus.bootcamp.nttdata.infraestructure.repository;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.LineOfCreditD;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para las lineas de creditos.
 *
 * @author pdiaz
 */
@ApplicationScoped
public class LineOfCreditRepository implements IRepository<LineOfCreditD> {
  /**
   * Devuelve una lista con todos los elementos no eliminados (softdelete)
   * de la base de datos LineOfCredit.
   *
   * @return Lista de lineas de credito.
   */
  @Override
  public List<LineOfCreditD> getAll() {
    return LineOfCreditD.listAll();
  }

  /**
   * Devuelve una linea de credito si existe.
   *
   * @param id Identificador de la linea de credito a devolver.
   * @return La linea de credito buscada.
   */
  @Override
  public LineOfCreditD getById(Long id) {
    Optional<LineOfCreditD> lineOfCreditD = LineOfCreditD.findByIdOptional(id);
    if (lineOfCreditD.isEmpty()) {
      throw new NullPointerException("Line of credit not found");
    }
    return lineOfCreditD.get();
  }

  /**
   * Guarda en la base de datos la información de la linea de credito.
   *
   * @param lineOfCreditD Linea de credito a guardar.
   * @return La linea de credito guardada.
   */
  @Override
  public LineOfCreditD save(LineOfCreditD lineOfCreditD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    String date = ZonedDateTime.now(ZoneId.systemDefault()).format(formatter);
    if (lineOfCreditD.getCreatedAt() == null) {
      lineOfCreditD.setCreatedAt(date);
    } else {
      lineOfCreditD.setUpdatedAt(date);
    }
    lineOfCreditD.persist();
    return lineOfCreditD;
  }

  /**
   * Realiza el borrado lógico de la linea de credito indicada.
   *
   * @param lineOfCreditD Linea de credito a borrar logicamente.
   * @return Linea de credito borrada.
   */
  @Override
  public LineOfCreditD softDelete(LineOfCreditD lineOfCreditD) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu.MM.dd.HH:mm:ss");
    lineOfCreditD.setDeletedAt(ZonedDateTime.now(ZoneId.systemDefault()).format(formatter));
    return this.save(lineOfCreditD);
  }
}
