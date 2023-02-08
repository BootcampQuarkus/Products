package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.entity.LineOfCredit;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.LineOfCreditMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.LineOfCreditD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.LineOfCreditRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class LineOfCreditService implements IService<LineOfCredit, LineOfCredit> {

  @Inject
  LineOfCreditRepository repository;
  @Inject
  LineOfCreditMapper mapper;

  @Override
  public List<LineOfCredit> getAll() {
    return repository.getAll()
          .stream()
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> mapper.toDto(p))
          .toList();
  }

  @Override
  public LineOfCredit getById(Long id) {
    return mapper.toDto(repository.getById(id));
  }

  @Override
  public LineOfCredit create(LineOfCredit lineOfCredit) {
    return mapper.toDto(repository.save(mapper.toEntity(lineOfCredit)));
  }

  @Override
  public LineOfCredit update(Long id, LineOfCredit lineOfCredit) {
    LineOfCreditD lineOfCreditD = repository.getById(id);
    lineOfCreditD.setAvailable(lineOfCredit.getAvailable());
    lineOfCreditD.setCosts(lineOfCredit.getCosts());
    lineOfCreditD.setAmount(lineOfCredit.getAmount());
    lineOfCreditD.setClosingDate(lineOfCredit.getClosingDate());
    lineOfCreditD.setPaymentDueDate(lineOfCredit.getPaymentDueDate());
    lineOfCreditD.setCutomerId(lineOfCredit.getCutomerId());
    return mapper.toDto(repository.save(lineOfCreditD));
  }

  @Override
  public LineOfCredit delete(Long id) {
    return mapper.toDto(repository.softDelete(repository.getById(id)));
  }
}
