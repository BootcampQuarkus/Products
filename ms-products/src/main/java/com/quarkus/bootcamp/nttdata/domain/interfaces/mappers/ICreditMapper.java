package com.quarkus.bootcamp.nttdata.domain.interfaces.mappers;

import com.quarkus.bootcamp.nttdata.domain.entity.Credit;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.CreditD;

public interface ICreditMapper {
  Credit toDto(CreditD creditD);

  CreditD toEntity(Credit credit);
}
