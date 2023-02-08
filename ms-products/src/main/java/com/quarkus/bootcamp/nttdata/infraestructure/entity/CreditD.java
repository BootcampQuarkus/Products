package com.quarkus.bootcamp.nttdata.infraestructure.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de base de datos para los creditos.
 *
 * @author pdiaz
 */
@Entity
@Table(name = "credit")
@Data
@NoArgsConstructor
public class CreditD extends PanacheEntity {
  /**
   * Monto aprobado del credito al momento de crear el producto.
   */
  protected Double amount;
  /**
   * Id del cliente al que le pertenece el producto
   */
  protected Long cutomerId;
  /**
   * El saldo por pagar
   */
  protected Double balance;
  /**
   * Número de cuotas
   */
  protected Integer dues;
  /**
   * Fecha de pago de las cuotas (dd-mm).
   */
  protected String paymentDueDate;
  /**
   * Fecha en la que se creó el crédito.
   */
  @Column(nullable = true)
  protected String createdAt = null;
  /**
   * Fecha de la ultima actualización del crédito.
   */
  @Column(nullable = true)
  protected String updatedAt = null;
  /**
   * Fecha en la que se eliminó el crédito.
   */
  @Column(nullable = true)
  protected String deletedAt = null;
}
