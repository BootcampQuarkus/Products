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
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> mapper.toEntity(p))
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
  public Account getById(Long id) {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> mapper.toEntity(p))
          .orElseThrow(() -> new NotFoundException());
  }

  /**
   * Crea un nuevo elemento en la BD
   *
   * @param account El elemento a crear.
   * @return El elemento creado.
   */
  @Override
  public Account create(Account account) {
    return mapper.toEntity(repository.save(mapper.toDto(account)));
  }

  /**
   * Actualiza un elemento en la BD. Se busca por el Id.
   *
   * @param id      Identificador del elemento ha editar.
   * @param account Elemento con los datos para guardar.
   * @return Retorna el elemento editado/actualizado.
   */
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

  /**
   * Realiza la eliminación del elemento de manera logica (softDelete).
   *
   * @param id Identificador del elemento a eliminar.
   * @return Retorna el elemento eliminado.
   */
  @Override
  public Account delete(Long id) {
    AccountD accountD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    return mapper.toEntity(repository.softDelete(accountD));
  }
}