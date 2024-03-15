package com.semillero.solicitudes.persistence.mappers;

import com.semillero.solicitudes.persistence.dto.RequestVacationDto;
import com.semillero.solicitudes.persistence.entities.RequestVacationEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RequestVacationMapper {
    RequestVacationDto requestVacationToRequestVacationDto(RequestVacationEntity requestVacationEntity);
    @InheritInverseConfiguration
    RequestVacationEntity requestVacationToRequestVacationEntity(RequestVacationDto requestVacationDto);

}
