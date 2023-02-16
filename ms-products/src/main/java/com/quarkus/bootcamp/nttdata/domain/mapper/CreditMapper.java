package com.quarkus.bootcamp.nttdata.domain.mapper;

import com.quarkus.bootcamp.nttdata.domain.entity.Credit;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.CreditD;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreditMapper implements IMapper<Credit, CreditD> {
  /**
   * Transforma el objeto de Credit a CreditD.
   *
   * @param credit Objeto de la clase Credit que se desea transformar.
   * @return Objeto de la clase CreditD.
   * @throws NullPointerException
   */
  @Override
  public CreditD toEntity(Credit credit) throws NullPointerException {
    CreditD creditD = new CreditD();
    creditD.setAmount(credit.getAmount());
    creditD.setCustomerId(credit.getCustomerId());
    creditD.setBalance(credit.getBalance());
    creditD.setDues(credit.getDues());
    creditD.setPaymentDueDate(credit.getPaymentDueDate());
    return creditD;
  }

  /**
   * Transforma el objeto de CreditD a Credit.
   *
   * @param creditD Objeto de la clase CreditD que se desea transformar.
   * @return Objeto de la clase Credit.
   * @throws NullPointerException
   */
  @Override
  public Credit toDto(CreditD creditD) throws NullPointerException {
    Credit credit = new Credit();
    credit.setId(creditD.id);
    credit.setAmount(creditD.getAmount());
    credit.setCustomerId(creditD.getCustomerId());
    credit.setBalance(creditD.getBalance());
    credit.setDues(creditD.getDues());
    credit.setPaymentDueDate(creditD.getPaymentDueDate());
    return credit;
  }

}
