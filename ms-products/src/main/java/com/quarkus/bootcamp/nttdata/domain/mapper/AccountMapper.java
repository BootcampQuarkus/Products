package com.quarkus.bootcamp.nttdata.domain.mapper;

import com.quarkus.bootcamp.nttdata.domain.entity.Account;
import com.quarkus.bootcamp.nttdata.domain.interfaces.mappers.IAccountMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.AccountD;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountMapper implements IAccountMapper {
  @Override
  public Account toDto(AccountD accountD) {
    Account account = new Account();
    account.setCardId(accountD.getCardId());
    account.setAmount(accountD.getAmount());
    account.setCutomerId(accountD.getCutomerId());
    return account;
  }

  @Override
  public AccountD toEntity(Account account) {
    AccountD accountD = new AccountD();
    accountD.setCardId(account.getCardId());
    accountD.setAmount(account.getAmount());
    accountD.setCutomerId(account.getCutomerId());
    return accountD;
  }
}
