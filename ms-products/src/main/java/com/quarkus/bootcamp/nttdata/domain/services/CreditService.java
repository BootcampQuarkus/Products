package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.entity.Credit;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.CreditMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.CreditD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.CreditRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CreditService implements IService<Credit, Credit> {
  @Inject
  CreditRepository repository;

  @Inject
  CreditMapper mapper;

  @Override
  public List<Credit> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> mapper.toDto(p))
          .toList();
  }

  @Override
  public Credit getById(Long id) {
    return mapper.toDto(repository.getById(id));
  }

  @Override
  public Credit create(Credit credit) {
    return mapper.toDto(repository.save(mapper.toEntity(credit)));
  }

  @Override
  public Credit update(Long id, Credit credit) {
    CreditD creditD = repository.getById(id);
    creditD.setAmount(credit.getAmount());
    creditD.setCutomerId(credit.getCutomerId());
    creditD.setBalance(credit.getBalance());
    creditD.setDues(credit.getDues());
    creditD.setPaymentDueDate(credit.getPaymentDueDate());
    return mapper.toDto(repository.save(creditD));
  }

  @Override
  public Credit delete(Long id) {
    return mapper.toDto(repository.softDelete(repository.getById(id)));
  }
}
