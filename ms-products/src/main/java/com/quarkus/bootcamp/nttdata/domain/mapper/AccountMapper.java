package com.quarkus.bootcamp.nttdata.domain.mapper;

import com.quarkus.bootcamp.nttdata.domain.entity.Account;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.AccountD;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountMapper implements IMapper<Account, AccountD> {
  /**
   * Transforma el objeto de Account a AccountD.
   *
   * @param account Objeto de la clase Account que se desea transformar.
   * @return Objeto de la clase AccountD.
   * @throws NullPointerException
   */
  @Override
  public AccountD toDto(Account account) throws NullPointerException {
    AccountD accountD = new AccountD();
    accountD.setCardId(account.getCardId());
    accountD.setAmount(account.getAmount());
    accountD.setCustomerId(account.getCustomerId());
    return accountD;
  }

  /**
   * Transforma el objeto de AccountD a Account.
   *
   * @param accountD Objeto de la clase AccountD que se desea transformar.
   * @return Objeto de la clase Account.
   * @throws NullPointerException
   */
  @Override
  public Account toEntity(AccountD accountD) throws NullPointerException {
    Account account = new Account();
    account.setId(accountD.id);
    account.setCardId(accountD.getCardId());
    account.setAmount(accountD.getAmount());
    account.setCustomerId(accountD.getCustomerId());
    return account;
  }

}
