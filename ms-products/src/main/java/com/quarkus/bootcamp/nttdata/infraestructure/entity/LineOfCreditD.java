package com.quarkus.bootcamp.nttdata.infraestructure.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de base de datos para las lineas de credito.
 *
 * @pdiaz
 */
@Entity
@Table(name = "lineOfCredit")
@Data
@NoArgsConstructor
public class LineOfCreditD extends PanacheEntity {
  /**
   * Monto de la linea de credito registrado al momento de crear el producto
   */
  protected Double amount;
  /**
   * Id del cliente al que le pertenece el producto
   */
  protected Long cutomerId;
  /**
   * Saldo disponible en la linea de credito
   */
  protected Double available;
  /**
   * Gastos de la linea de credito.
   * Lo consumido.
   */
  protected Double costs;
  /**
   * Fecha de cierre para el calculo del pago (dd-mm)
   */
  protected String closingDate;
  /**
   * Fecha de pago de la cuota (dd-mm).
   */
  protected String paymentDueDate;
  /**
   * Fecha en la que se creó la linea de crédito.
   */
  @Column(nullable = true)
  protected String createdAt = null;
  /**
   * Fecha de la ultima actualización la linea de crédito.
   */
  @Column(nullable = true)
  protected String updatedAt = null;
  /**
   * Fecha en la que se eliminó la linea de crédito.
   */
  @Column(nullable = true)
  protected String deletedAt = null;
}
