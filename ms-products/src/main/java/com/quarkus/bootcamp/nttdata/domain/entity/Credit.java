package com.quarkus.bootcamp.nttdata.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de la logica de negocio para los creditos.
 *
 * @author pdiaz
 */
@Data
@NoArgsConstructor
public class Credit extends Product {
  /**
   * El saldo por pagar
   */
  protected Double balance;
  /**
   * Número de cuotas
   */
  protected Integer dues;
  /**
   * Fecha de pago de las cuotas (dd).
   */
  protected String paymentDueDate;
}
