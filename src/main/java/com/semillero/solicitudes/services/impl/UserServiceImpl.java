package com.semillero.solicitudes.services.impl;

import com.semillero.solicitudes.exceptions.ResourceNotFoundException;
import com.semillero.solicitudes.persistence.dto.UserDto;
import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import com.semillero.solicitudes.persistence.entities.UserEntity;
import com.semillero.solicitudes.persistence.mappers.UserMapper;
import com.semillero.solicitudes.persistence.repositories.EmployeeRepository;
import com.semillero.solicitudes.persistence.repositories.UserRepository;
import com.semillero.solicitudes.services.interfaces.IUserService;
import com.semillero.solicitudes.utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto, Long employeeId) {
        verifyEmployeeExistence(employeeId);
        EmployeeEntity employee = this.employeeRepository.findById(employeeId).get();
        UserEntity userCreate = createUserTemp(userDto, employee);

        UserEntity userSave = this.userRepository.save(userCreate);
        return this.userMapper.userToUserDto(userSave);
    }

    private void verifyEmployeeExistence(Long employeeId) {
        if (!this.employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException(Constants.EMPLOYEE_NOT_FOUND_MESSAGE + employeeId);
        }
    }

    private UserEntity createUserTemp(UserDto userDto, EmployeeEntity employee) {
        UserEntity userEntity = new UserEntity();
        userEntity.setDsUsername(userDto.getDsUsername());
        userEntity.setDsPassword(userDto.getDsPassword());
        userEntity.setDsEmail(userDto.getDsEmail());
        userEntity.setDsUserStatus(userDto.getDsUserStatus());
        userEntity.setEmployeeEntity(employee);
        return userEntity;
    }

}