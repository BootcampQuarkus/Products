package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.entity.Account;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.AccountMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.AccountD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.AccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

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
          .map(p -> mapper.toEntity(p))
          .toList();
  }

  @Override
  public Account getById(Long id) {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> mapper.toEntity(p))
          .orElseThrow(() -> new NotFoundException());
  }

  @Override
  public Account create(Account account) {
    return mapper.toEntity(repository.save(mapper.toDto(account)));
  }

  @Override
  public Account update(Long id, Account account) {
    AccountD accountD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    accountD.setCardId(account.getCardId());
    accountD.setAmount(account.getAmount());
    accountD.setCutomerId(account.getCutomerId());
    return mapper.toEntity(repository.save(accountD));
  }

  @Override
  public Account delete(Long id) {
    AccountD accountD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    return mapper.toEntity(repository.softDelete(accountD));
  }
}
