package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.entity.LineOfCredit;
import com.quarkus.bootcamp.nttdata.domain.interfaces.IService;
import com.quarkus.bootcamp.nttdata.domain.mapper.LineOfCreditMapper;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.LineOfCreditD;
import com.quarkus.bootcamp.nttdata.infraestructure.repository.LineOfCreditRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

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
          .map(p -> mapper.toEntity(p))
          .toList();
  }

  @Override
  public LineOfCredit getById(Long id) {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> mapper.toEntity(p))
          .orElseThrow(() -> new NotFoundException());
  }

  @Override
  public LineOfCredit create(LineOfCredit lineOfCredit) {
    return mapper.toEntity(repository.save(mapper.toDto(lineOfCredit)));
  }

  @Override
  public LineOfCredit update(Long id, LineOfCredit lineOfCredit) {
    LineOfCreditD lineOfCreditD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    lineOfCreditD.setAvailable(lineOfCredit.getAvailable());
    lineOfCreditD.setCosts(lineOfCredit.getCosts());
    lineOfCreditD.setAmount(lineOfCredit.getAmount());
    lineOfCreditD.setClosingDate(lineOfCredit.getClosingDate());
    lineOfCreditD.setPaymentDueDate(lineOfCredit.getPaymentDueDate());
    lineOfCreditD.setCutomerId(lineOfCredit.getCutomerId());
    return mapper.toEntity(repository.save(lineOfCreditD));
  }

  @Override
  public LineOfCredit delete(Long id) {
    LineOfCreditD lineOfCreditD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    return mapper.toEntity(repository.softDelete(lineOfCreditD));
  }
}
