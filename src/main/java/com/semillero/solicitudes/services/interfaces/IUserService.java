package com.semillero.solicitudes.services.interfaces;

import com.semillero.solicitudes.persistence.dto.UserDto;

import java.util.List;

public interface IUserService {
    public List<UserDto> getAllUsers();
    public UserDto createUser(UserDto userDto, Long employeeId);

}
