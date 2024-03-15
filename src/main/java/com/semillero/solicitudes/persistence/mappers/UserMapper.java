package com.semillero.solicitudes.persistence.mappers;

import com.semillero.solicitudes.persistence.dto.UserDto;
import com.semillero.solicitudes.persistence.entities.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto userToUserDto(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity userToUserEntity(UserDto userDto);

}
