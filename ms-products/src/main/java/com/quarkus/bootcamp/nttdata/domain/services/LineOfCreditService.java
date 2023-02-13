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
  public LineOfCredit getById(Long id) {
    return repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .map(p -> mapper.toEntity(p))
          .orElseThrow(() -> new NotFoundException());
  }

  /**
   * Crea un nuevo elemento en la BD
   *
   * @param lineOfCredit El elemento a crear.
   * @return El elemento creado.
   */
  @Override
  public LineOfCredit create(LineOfCredit lineOfCredit) {
    return mapper.toEntity(repository.save(mapper.toDto(lineOfCredit)));
  }

  /**
   * Actualiza un elemento en la BD. Se busca por el Id.
   *
   * @param id           Identificador del elemento ha editar.
   * @param lineOfCredit Elemento con los datos para guardar.
   * @return Retorna el elemento editado/actualizado.
   */
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
    lineOfCreditD.setCustomerId(lineOfCredit.getCustomerId());
    return mapper.toEntity(repository.save(lineOfCreditD));
  }

  /**
   * Realiza la eliminación del elemento de manera logica (softDelete).
   *
   * @param id Identificador del elemento a eliminar.
   * @return Retorna el elemento eliminado.
   */
  @Override
  public LineOfCredit delete(Long id) {
    LineOfCreditD lineOfCreditD = repository.findByIdOptional(id)
          .filter(p -> (p.getDeletedAt() == null))
          .orElseThrow(() -> new NotFoundException());
    return mapper.toEntity(repository.softDelete(lineOfCreditD));
  }
}