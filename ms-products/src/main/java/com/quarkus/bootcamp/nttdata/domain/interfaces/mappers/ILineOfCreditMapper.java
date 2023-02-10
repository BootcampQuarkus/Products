package com.quarkus.bootcamp.nttdata.domain.interfaces.mappers;

import com.quarkus.bootcamp.nttdata.domain.entity.LineOfCredit;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.LineOfCreditD;

public interface ILineOfCreditMapper {
  LineOfCreditD toDto(LineOfCredit lineOfCredit);

  LineOfCredit toEntity(LineOfCreditD lineOfCreditD);
}
