package com.semillero.solicitudes.services.interfaces;

import com.semillero.solicitudes.persistence.dto.UserDto;

public interface IUserService {
    public UserDto createUser(UserDto userDto, Long employeeId);

}
