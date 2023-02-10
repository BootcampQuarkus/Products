package com.quarkus.bootcamp.nttdata.domain.mapper;

import com.quarkus.bootcamp.nttdata.domain.entity.LineOfCredit;
import com.quarkus.bootcamp.nttdata.domain.interfaces.mappers.ILineOfCreditMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.LineOfCreditD;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LineOfCreditMapper implements ILineOfCreditMapper {
  @Override
  public LineOfCreditD toDto(LineOfCredit lineOfCredit) {
    LineOfCreditD lineOfCreditD = new LineOfCreditD();
    lineOfCreditD.setAmount(lineOfCredit.getAmount());
    lineOfCreditD.setAvailable(lineOfCredit.getAvailable());
    lineOfCreditD.setClosingDate(lineOfCredit.getClosingDate());
    lineOfCreditD.setCosts(lineOfCredit.getCosts());
    lineOfCreditD.setCutomerId(lineOfCredit.getCutomerId());
    lineOfCreditD.setPaymentDueDate(lineOfCredit.getPaymentDueDate());
    return lineOfCreditD;
  }

  @Override
  public LineOfCredit toEntity(LineOfCreditD lineOfCreditD) {
    LineOfCredit lineOfCredit = new LineOfCredit();
    lineOfCredit.setAmount(lineOfCreditD.getAmount());
    lineOfCredit.setAvailable(lineOfCreditD.getAvailable());
    lineOfCredit.setClosingDate(lineOfCreditD.getClosingDate());
    lineOfCredit.setCosts(lineOfCreditD.getCosts());
    lineOfCredit.setCutomerId(lineOfCreditD.getCutomerId());
    lineOfCredit.setPaymentDueDate(lineOfCreditD.getPaymentDueDate());
    return lineOfCredit;
  }
}
