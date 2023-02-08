package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.entity.Account;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.AccountMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.AccountD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.AccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;


@ApplicationScoped
public class AccountService implements IService<Account, Account> {
  @Inject
  AccountRepository repository;

  @Inject
  AccountMapper mapper;

  @Override
  public List<Account> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> mapper.toDto(p))
          .toList();
  }

  @Override
  public Account getById(Long id) {
    return mapper.toDto(repository.getById(id));
  }

  @Override
  public Account create(Account account) {
    return mapper.toDto(repository.save(mapper.toEntity(account)));
  }

  @Override
  public Account update(Long id, Account account) {
    AccountD accountD = repository.getById(id);
    accountD.setCardId(account.getCardId());
    accountD.setAmount(account.getAmount());
    accountD.setCutomerId(account.getCutomerId());
    return mapper.toDto(repository.save(accountD));
  }

  @Override
  public Account delete(Long id) {
    return mapper.toDto(repository.softDelete(repository.getById(id)));
  }
}
