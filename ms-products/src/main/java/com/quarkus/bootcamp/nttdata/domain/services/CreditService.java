package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.CreditNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.CustomerNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Util;
import com.quarkus.bootcamp.nttdata.domain.entity.Account;
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
  @Inject
  Util util;

  /**
   * Retorna todos los elementos guardados en la bd
   * que no estén borrados (softDelete).
   *
   * @return Lista de elementos.
   */
  @Override
  public List<Credit> getAll() {
    return repository.getAll()
          .stream()
          .filter(util::notDelete)
          .map(mapper::toDto)
          .toList();
  }

  public List<Credit> getAll(Long customerId) {
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
  public Credit getById(Long id) throws CreditNotFoundException {
    return repository.findByIdOptional(id)
          .filter(util::notDelete)
          .map(mapper::toDto)
          .orElseThrow(() -> new CreditNotFoundException("Credit not found."));
  }

  /**
   * Crea un nuevo elemento en la BD
   *
   * @param credit El elemento a crear.
   * @return El elemento creado.
   */
  @Override
  public Credit create(Credit credit) throws CustomerNotFoundException {
    util.validateCustomer(credit.getCustomerId());
    return mapper.toDto(repository.save(mapper.toEntity(credit)));
  }

  /**
   * Actualiza un elemento en la BD. Se busca por el Id.
   *
   * @param id     Identificador del elemento ha editar.
   * @param credit Elemento con los datos para guardar.
   * @return Retorna el elemento editado/actualizado.
   */
  @Override
  public Credit update(Long id, Credit credit) throws CustomerNotFoundException, CreditNotFoundException {
    util.validateCustomer(credit.getCustomerId());
    CreditD creditD = repository.findByIdOptional(id)
          .filter(util::notDelete)
          .orElseThrow(() -> new CreditNotFoundException("Credit not found."));
    creditD.setAmount(credit.getAmount());
    creditD.setCustomerId(credit.getCustomerId());
    creditD.setBalance(credit.getBalance());
    creditD.setDues(credit.getDues());
    creditD.setPaymentDueDate(credit.getPaymentDueDate());
    return mapper.toDto(repository.save(creditD));
  }

  /**
   * Realiza la eliminación del elemento de manera logica (softDelete).
   *
   * @param id Identificador del elemento a eliminar.
   * @return Retorna el elemento eliminado.
   */
  @Override
  public Credit delete(Long id) throws CreditNotFoundException {
    CreditD creditD = repository.findByIdOptional(id)
          .filter(util::notDelete)
          .orElseThrow(() -> new CreditNotFoundException("Credit not found."));
    return mapper.toDto(repository.softDelete(creditD));
  }
}