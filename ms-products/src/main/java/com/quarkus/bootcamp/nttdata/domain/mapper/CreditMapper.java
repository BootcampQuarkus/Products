package com.quarkus.bootcamp.nttdata.domain.mapper;

import com.quarkus.bootcamp.nttdata.domain.entity.Credit;
import com.quarkus.bootcamp.nttdata.domain.interfaces.mappers.ICreditMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.CreditD;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreditMapper implements ICreditMapper {
  @Override
  public Credit toDto(CreditD creditD) {
    Credit credit = new Credit();
    credit.setAmount(creditD.getAmount());
    credit.setCutomerId(creditD.getCutomerId());
    credit.setBalance(creditD.getBalance());
    credit.setDues(creditD.getDues());
    credit.setPaymentDueDate(creditD.getPaymentDueDate());
    return credit;
  }

  @Override
  public CreditD toEntity(Credit credit) {
    CreditD creditD = new CreditD();
    creditD.setAmount(credit.getAmount());
    creditD.setCutomerId(credit.getCutomerId());
    creditD.setBalance(credit.getBalance());
    creditD.setDues(credit.getDues());
    creditD.setPaymentDueDate(credit.getPaymentDueDate());
    return creditD;
  }
}
