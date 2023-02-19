package com.quarkus.bootcamp.nttdata.domain.mapper;

import com.quarkus.bootcamp.nttdata.domain.entity.LineOfCredit;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.LineOfCreditD;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LineOfCreditMapper implements IMapper<LineOfCredit, LineOfCreditD> {
  /**
   * Transforma el objeto de LineOfCredit a LineOfCreditD.
   *
   * @param lineOfCredit Objeto de la clase LineOfCredit que se desea transformar.
   * @return Objeto de la clase LineOfCreditD.
   * @throws NullPointerException
   */
  @Override
  public LineOfCreditD toEntity(LineOfCredit lineOfCredit) throws NullPointerException {
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    lineOfCreditD.setAmount(lineOfCredit.getAmount());
    lineOfCreditD.setAvailable(lineOfCredit.getAvailable());
    lineOfCreditD.setClosingDate(lineOfCredit.getClosingDate());
    lineOfCreditD.setCosts(lineOfCredit.getCosts());
    lineOfCreditD.setCustomerId(lineOfCredit.getCustomerId());
    lineOfCreditD.setPaymentDueDate(lineOfCredit.getPaymentDueDate());
    return lineOfCreditD;
  }

  /**
   * Transforma el objeto de LineOfCreditD a LineOfCredit.
   *
   * @param lineOfCreditD Objeto de la clase LineOfCreditD que se desea transformar.
   * @return Objeto de la clase LineOfCredit.
   * @throws NullPointerException
   */
  @Override
  public LineOfCredit toDto(LineOfCreditD lineOfCreditD) throws NullPointerException {
    LineOfCredit lineOfCredit = new LineOfCredit();
    lineOfCredit.setId(lineOfCreditD.id);
    lineOfCredit.setAmount(lineOfCreditD.getAmount());
    lineOfCredit.setAvailable(lineOfCreditD.getAvailable());
    lineOfCredit.setClosingDate(lineOfCreditD.getClosingDate());
    lineOfCredit.setCosts(lineOfCreditD.getCosts());
    lineOfCredit.setCustomerId(lineOfCreditD.getCustomerId());
    lineOfCredit.setPaymentDueDate(lineOfCreditD.getPaymentDueDate());
    return lineOfCredit;
  }
}
