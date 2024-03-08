package com.semillero.solicitudes.persistence.mappers;

import com.semillero.solicitudes.persistence.dto.UserDto;
import com.semillero.solicitudes.persistence.entities.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDto userToUserDto(UserEntity userEntity);
    @InheritInverseConfiguration
    UserEntity userToUserEntity(UserDto userDto);
}
