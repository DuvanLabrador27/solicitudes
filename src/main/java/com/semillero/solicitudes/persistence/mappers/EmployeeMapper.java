package com.semillero.solicitudes.persistence.mappers;

import com.semillero.solicitudes.persistence.dto.EmployeeDto;
import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    EmployeeDto employeeToEmployeeDto(EmployeeEntity employeeEntity);

    @InheritInverseConfiguration
    EmployeeEntity employeeToEmployeeEntity(EmployeeDto requestEmployeeDto);

}
