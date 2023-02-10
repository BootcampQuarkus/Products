package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.entity.Credit;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.CreditMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.CreditD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.CreditRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

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
          .map(p -> mapper.toEntity(p))
          .toList();
  }

  @Override
  public Credit getById(Long id) {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> mapper.toEntity(p))
          .orElseThrow(() -> new NotFoundException());
  }

  @Override
  public Credit create(Credit credit) {
    return mapper.toEntity(repository.save(mapper.toDto(credit)));
  }

  @Override
  public Credit update(Long id, Credit credit) {
    CreditD creditD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    creditD.setAmount(credit.getAmount());
    creditD.setCutomerId(credit.getCutomerId());
    creditD.setBalance(credit.getBalance());
    creditD.setDues(credit.getDues());
    creditD.setPaymentDueDate(credit.getPaymentDueDate());
    return mapper.toEntity(repository.save(creditD));
  }

  @Override
  public Credit delete(Long id) {
    CreditD creditD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    return mapper.toEntity(repository.softDelete(creditD));
  }
}
