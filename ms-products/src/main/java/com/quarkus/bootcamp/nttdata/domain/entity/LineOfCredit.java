package com.quarkus.bootcamp.nttdata.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de la logica de negocio para las lineas de credito.
 *
 * @author pdiaz
 */
@Data
@NoArgsConstructor
public class LineOfCredit extends Product {
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
   * Fecha de pago de la cuota (dd).
   */
  protected String paymentDueDate;

  public LineOfCredit(Double amount, Long cutomerId, Double available, Double costs, String closingDate, String paymentDueDate) {
    super(amount, cutomerId);
    this.available = available;
    this.costs = costs;
    this.closingDate = closingDate;
    this.paymentDueDate = paymentDueDate;
  }
}
