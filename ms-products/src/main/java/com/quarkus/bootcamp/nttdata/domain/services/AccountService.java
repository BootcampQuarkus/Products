package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.Exceptions.AccountNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Exceptions.CustomerNotFoundException;
import com.quarkus.bootcamp.nttdata.domain.Util;
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
  @Inject
  Util util;

  /**
   * Retorna todos los elementos guardados en la bd
   * que no estén borrados (softDelete).
   *
   * @return Lista de entities.
   */
  @Override
  public List<Account> getAll() {
    return repository.getAll()
          .stream()
          .filter(util::notDelete)
          .map(mapper::toDto)
          .toList();
  }

  public List<Account> getAll(Long customerId) {
    return repository.findByCustomerId(customerId)
          .stream()
          .filter(util::notDelete)
          .map(mapper::toDto)
          .toList();
  }

  /**
   * Se busca por el id en la bd y de existir
   * retorna el entity, de lo contrario retorna
   * null
   *
   * @param id Id del account en la BD.
   * @return De existir el elemento lo retorna, de lo contrario null .
   */
  @Override
  public Account getById(Long id) throws AccountNotFoundException {
    return repository.findByIdOptional(id)
          .filter(util::notDelete)
          .map(mapper::toDto)
          .orElseThrow(() -> new AccountNotFoundException("Account not found."));
  }

  /**
   * Crea un nuevo elemento en la BD
   *
   * @param account El elemento a crear.
   * @return El elemento creado.
   */
  @Override
  public Account create(Account account) throws CustomerNotFoundException {
    util.validateCustomer(account.getCustomerId());
    return mapper.toDto(repository.save(mapper.toEntity(account)));
  }

  /**
   * Actualiza un elemento en la BD. Se busca por el Id.
   *
   * @param id      Identificador del elemento ha editar.
   * @param account Elemento con los datos para guardar.
   * @return Retorna el elemento editado/actualizado.
   */
  @Override
  public Account update(Long id, Account account) throws CustomerNotFoundException, AccountNotFoundException {
    util.validateCustomer(account.getCustomerId());
    AccountD accountD = repository.findByIdOptional(id)
          .filter(util::notDelete)
          .orElseThrow(() -> new AccountNotFoundException("Account not found."));
    accountD.setCardId(account.getCardId());
    accountD.setAmount(account.getAmount());
    accountD.setCustomerId(account.getCustomerId());
    return mapper.toDto(repository.save(accountD));
  }

  /**
   * Realiza la eliminación del elemento de manera logica (softDelete).
   *
   * @param id Identificador del elemento a eliminar.
   * @return Retorna el elemento eliminado.
   */
  @Override
  public Account delete(Long id) throws AccountNotFoundException {
    AccountD accountD = repository.findByIdOptional(id)
          .filter(util::notDelete)
          .orElseThrow(() -> new AccountNotFoundException("Account not found."));
    return mapper.toDto(repository.softDelete(accountD));
  }

}