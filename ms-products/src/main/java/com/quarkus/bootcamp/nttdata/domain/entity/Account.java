package com.quarkus.bootcamp.nttdata.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de logica de negocio para las cuentas.
 *
 * @author pdiaz
 */
@Data
@NoArgsConstructor
public class Account extends Product {
  /**
   * Id de la tarjeta vinculada a dicha cuenta
   */
  protected Long cardId;
}
