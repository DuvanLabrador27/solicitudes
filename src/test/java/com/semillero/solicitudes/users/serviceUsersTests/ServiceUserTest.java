package com.semillero.solicitudes.users.serviceUsersTests;

import com.semillero.solicitudes.persistence.dto.EmployeeDto;
import com.semillero.solicitudes.persistence.dto.UserDto;
import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import com.semillero.solicitudes.persistence.entities.UserEntity;
import com.semillero.solicitudes.persistence.enums.DocumentTypes;
import com.semillero.solicitudes.persistence.enums.TypeOfContract;
import com.semillero.solicitudes.persistence.enums.UserStatus;
import com.semillero.solicitudes.persistence.mappers.UserMapper;
import com.semillero.solicitudes.persistence.repositories.EmployeeRepository;
import com.semillero.solicitudes.persistence.repositories.UserRepository;
import com.semillero.solicitudes.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ServiceUserTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity userEntity;
    private UserDto userDto;
    private UserEntity userEntityTwo;
    private UserDto userDtoTwo;

    private EmployeeEntity employeeEntity;
    private EmployeeDto employeeDto;
    private EmployeeEntity employeeEntityTwo;
    private EmployeeDto employeeDtoTwo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userEntity = createUserEntity(1L,"leo","123","leo@gmail.com",UserStatus.ACTIVE,new EmployeeEntity());
        userDto = createUserDto(1L,"leo","123","leo@gmail.com",UserStatus.ACTIVE);

        userEntityTwo = createUserEntity(2L,"cr7","123","cr7@gmail.com",UserStatus.ACTIVE, new EmployeeEntity());
        userDtoTwo = createUserDto(2L,"cr7","123","cr7@gmail.com",UserStatus.ACTIVE);

        employeeEntity = createEmployeeEntity(1L, "Leo", "Messi", "123456", "366468383", "Argentina",
                LocalDate.parse("2023-01-05"), TypeOfContract.INDEFINITE_TERM, UserStatus.ACTIVE);
        employeeDto = createEmployeeDto(1L, "Leo", "Messi", "123456", "366468383", "Argentina",
                LocalDate.parse("2023-01-05"), TypeOfContract.INDEFINITE_TERM, UserStatus.ACTIVE);

        employeeEntityTwo = createEmployeeEntity(2L, "CR7", "Ronaldo", "1234567", "366468383", "Portugal",
                LocalDate.parse("2023-01-05"), TypeOfContract.INDEFINITE_TERM, UserStatus.ACTIVE);
        employeeDtoTwo = createEmployeeDto(2L, "CR7", "Ronaldo", "1234567", "366468383", "Portugal",
                LocalDate.parse("2023-01-05"), TypeOfContract.INDEFINITE_TERM, UserStatus.ACTIVE);
    }

    @DisplayName("Create User")
    @Test
    public void testCreateUser(){
        Mockito.when(employeeRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(employeeEntity));
        when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.userToUserDto(Mockito.any()))
                .thenReturn(userDto);

        UserDto user = userService.createUser(userDto, employeeDto.getNmIdEmployee());
        assertThat(user).isNotNull();

    }

    @DisplayName("Get all Users")
    @Test
    public void testGetAllUsers(){
        List<UserEntity> userEntities = Arrays.asList(userEntity,userEntityTwo);
        when(userRepository.findAll()).thenReturn( userEntities);
        when(userMapper.userToUserDto(Mockito.any())).thenReturn(userDto, userDtoTwo);

        List<UserDto> userList = userService.getAllUsers();
        userList.stream().forEach(users -> System.out.println(users));
        assertThat(userList).isNotNull();
    }


    private UserEntity createUserEntity(Long id, String username, String password, String email, UserStatus userStatus, EmployeeEntity employee) {
        return UserEntity.builder()
                .nmIdUser(id)
                .dsUsername(username)
                .dsPassword(password)
                .dsEmail(email)
                .dsUserStatus(userStatus)
                .employeeEntity(employeeEntity)
                .build();
    }

    private UserDto createUserDto(Long id, String username, String password, String email, UserStatus userStatus) {
        return UserDto.builder()
                .nmIdUser(id)
                .dsUsername(username)
                .dsPassword(password)
                .dsEmail(email)
                .dsUserStatus(userStatus)
                .build();
    }

    private EmployeeEntity createEmployeeEntity(Long id, String name, String lastName, String document, String phoneNumber, String address,
                                                LocalDate hireDate, TypeOfContract typeOfContract, UserStatus employeeStatus) {
        return EmployeeEntity.builder()
                .nmIdEmployee(id)
                .dsDocument(document)
                .dsDocumentType(DocumentTypes.CC)
                .dsName(name)
                .dsLastname(lastName)
                .dsPhoneNumber(phoneNumber)
                .dsAddress(address)
                .feHireDate(hireDate)
                .feDepartureDate(null)
                .dsTypeOfContract(typeOfContract)
                .dsEmployeeStatus(employeeStatus)
                .build();
    }

    private EmployeeDto createEmployeeDto(Long id, String name, String lastName, String document, String phoneNumber, String address,
                                          LocalDate hireDate, TypeOfContract typeOfContract, UserStatus employeeStatus) {
        return EmployeeDto.builder()
                .nmIdEmployee(id)
                .dsDocument(document)
                .dsDocumentType(DocumentTypes.CC)
                .dsName(name)
                .dsLastname(lastName)
                .dsPhoneNumber(phoneNumber)
                .dsAddress(address)
                .feHireDate(hireDate)
                .feDepartureDate(null)
                .dsTypeOfContract(typeOfContract)
                .dsEmployeeStatus(employeeStatus)
                .build();
    }
}
