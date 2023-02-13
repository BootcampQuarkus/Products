package com.quarkus.bootcamp.nttdata.infraestructure.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de BD para las cuentas
 *
 * @author pdiaz
 */
@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
public class AccountD extends PanacheEntity {
  /**
   * Monto inicial registrado al momento de crear el producto
   */
  protected Double amount;
  /**
   * Id del cliente al que le pertenece el producto
   */
  protected Long customerId;
  /**
   * Id de la tarjeta vinculada a dicha cuenta
   */
  protected Long cardId;
  /**
   * Fecha en la que se creó la cuenta.
   */
  @Column(nullable = true)
  protected String createdAt = null;
  /**
   * Fecha de la ultima actualización la cuenta.
   */
  @Column(nullable = true)
  protected String updatedAt = null;
  /**
   * Fecha en la que se eliminó la cuenta.
   */
  @Column(nullable = true)
  protected String deletedAt = null;
}
