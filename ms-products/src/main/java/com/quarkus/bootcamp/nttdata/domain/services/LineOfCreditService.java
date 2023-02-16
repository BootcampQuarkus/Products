package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.CustomerNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.LineOfCreditNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Util;
import com.quarkus.bootcamp.nttdata.domain.entity.Account;
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
  @Inject
  Util util;

  /**
   * Retorna todos los elementos guardados en la bd
   * que no estén borrados (softDelete).
   *
   * @return Lista de elementos.
   */
  @Override
  public List<LineOfCredit> getAll() {
    return repository.getAll()
          .stream()
          .filter(util::notDelete)
          .map(mapper::toDto)
          .toList();
  }

  public List<LineOfCredit> getAll(Long customerId) {
    return repository.findByCustomerId(customerId)
          .stream()
          .filter(util::notDelete)
          .map(mapper::toDto)
          .toList();
  }

  /**
   * Se busca por el id en la bd y de existir
   * retorna el elemento, de lo contrario retorna
   * null
   *
   * @param id Id del elemento en la BD.
   * @return De existir el elemento lo retorna, de lo contrario null .
   */
  @Override
  public LineOfCredit getById(Long id) throws LineOfCreditNotFoundException {
    return repository.findByIdOptional(id)
          .filter(util::notDelete)
          .map(mapper::toDto)
          .orElseThrow(() -> new LineOfCreditNotFoundException("Line of credit not found."));
  }

  /**
   * Crea un nuevo elemento en la BD
   *
   * @param lineOfCredit El elemento a crear.
   * @return El elemento creado.
   */
  @Override
  public LineOfCredit create(LineOfCredit lineOfCredit) throws CustomerNotFoundException {
    util.validateCustomer(lineOfCredit.getCustomerId());
    return mapper.toDto(repository.save(mapper.toEntity(lineOfCredit)));
  }

  /**
   * Actualiza un elemento en la BD. Se busca por el Id.
   *
   * @param id           Identificador del elemento ha editar.
   * @param lineOfCredit Elemento con los datos para guardar.
   * @return Retorna el elemento editado/actualizado.
   */
  @Override
  public LineOfCredit update(Long id, LineOfCredit lineOfCredit) throws CustomerNotFoundException, LineOfCreditNotFoundException {
    util.validateCustomer(lineOfCredit.getCustomerId());
    LineOfCreditD lineOfCreditD = repository.findByIdOptional(id)
          .filter(util::notDelete)
          .orElseThrow(() -> new LineOfCreditNotFoundException("Line of credit not found."));
    lineOfCreditD.setAvailable(lineOfCredit.getAvailable());
    lineOfCreditD.setCosts(lineOfCredit.getCosts());
    lineOfCreditD.setAmount(lineOfCredit.getAmount());
    lineOfCreditD.setClosingDate(lineOfCredit.getClosingDate());
    lineOfCreditD.setPaymentDueDate(lineOfCredit.getPaymentDueDate());
    lineOfCreditD.setCustomerId(lineOfCredit.getCustomerId());
    return mapper.toDto(repository.save(lineOfCreditD));
  }

  /**
   * Realiza la eliminación del elemento de manera logica (softDelete).
   *
   * @param id Identificador del elemento a eliminar.
   * @return Retorna el elemento eliminado.
   */
  @Override
  public LineOfCredit delete(Long id) throws LineOfCreditNotFoundException {
    LineOfCreditD lineOfCreditD = repository.findByIdOptional(id)
          .filter(util::notDelete)
          .orElseThrow(() -> new LineOfCreditNotFoundException("Line of credit not found."));
    return mapper.toDto(repository.softDelete(lineOfCreditD));
  }
}