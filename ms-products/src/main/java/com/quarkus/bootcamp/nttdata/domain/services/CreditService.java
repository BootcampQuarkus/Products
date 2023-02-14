package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.Util;
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
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> mapper.toEntity(p))
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
  public Credit getById(Long id) {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> mapper.toEntity(p))
          .orElseThrow(() -> new NotFoundException());
  }

  /**
   * Crea un nuevo elemento en la BD
   *
   * @param credit El elemento a crear.
   * @return El elemento creado.
   */
  @Override
  public Credit create(Credit credit) {
    util.validateCustomer(credit.getCustomerId());
    return mapper.toEntity(repository.save(mapper.toDto(credit)));
  }

  /**
   * Actualiza un elemento en la BD. Se busca por el Id.
   *
   * @param id     Identificador del elemento ha editar.
   * @param credit Elemento con los datos para guardar.
   * @return Retorna el elemento editado/actualizado.
   */
  @Override
  public Credit update(Long id, Credit credit) {
    util.validateCustomer(credit.getCustomerId());
    CreditD creditD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    creditD.setAmount(credit.getAmount());
    creditD.setCustomerId(credit.getCustomerId());
    creditD.setBalance(credit.getBalance());
    creditD.setDues(credit.getDues());
    creditD.setPaymentDueDate(credit.getPaymentDueDate());
    return mapper.toEntity(repository.save(creditD));
  }

  /**
   * Realiza la eliminación del elemento de manera logica (softDelete).
   *
   * @param id Identificador del elemento a eliminar.
   * @return Retorna el elemento eliminado.
   */
  @Override
  public Credit delete(Long id) {
    CreditD creditD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    return mapper.toEntity(repository.softDelete(creditD));
  }
}