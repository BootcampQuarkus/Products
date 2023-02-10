package com.quarkus.bootcamp.nttdata.domain.interfaces.mappers;

import com.quarkus.bootcamp.nttdata.domain.entity.Account;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.AccountD;

public interface IAccountMapper {
  AccountD toDto(Account account);

  Account toEntity(AccountD accountD);

}
