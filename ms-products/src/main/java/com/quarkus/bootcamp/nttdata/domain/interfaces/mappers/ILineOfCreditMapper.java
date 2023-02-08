package com.quarkus.bootcamp.nttdata.domain.interfaces.mappers;

import com.quarkus.bootcamp.nttdata.domain.entity.LineOfCredit;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.LineOfCreditD;

public interface ILineOfCreditMapper {
  LineOfCredit toDto(LineOfCreditD lineOfCreditD);

  LineOfCreditD toEntity(LineOfCredit lineOfCredit);
}
