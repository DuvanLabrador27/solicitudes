package com.semillero.solicitudes.requestVacation.serviceRequestVacationTests;

import com.semillero.solicitudes.persistence.dto.EmployeeDto;
import com.semillero.solicitudes.persistence.dto.RequestVacationDto;
import com.semillero.solicitudes.persistence.dto.UserDto;
import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import com.semillero.solicitudes.persistence.entities.RequestVacationEntity;
import com.semillero.solicitudes.persistence.entities.UserEntity;
import com.semillero.solicitudes.persistence.enums.DocumentTypes;
import com.semillero.solicitudes.persistence.enums.TypeOfContract;
import com.semillero.solicitudes.persistence.enums.UserStatus;
import com.semillero.solicitudes.persistence.mappers.RequestVacationMapper;
import com.semillero.solicitudes.persistence.repositories.RequestVacationRepository;
import com.semillero.solicitudes.persistence.repositories.UserRepository;
import com.semillero.solicitudes.services.impl.RequestVacationServiceImpl;
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

public class ServiceRequestVacationTest {

    @Mock
    private RequestVacationRepository requestVacationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RequestVacationMapper requestVacationMapper;
    private UserEntity userEntity;
    private UserDto userDto;
    private UserEntity userEntityTwo;
    private UserDto userDtoTwo;

    private EmployeeEntity employeeEntity;
    private EmployeeDto employeeDto;
    private EmployeeEntity employeeEntityTwo;
    private EmployeeDto employeeDtoTwo;

    private RequestVacationEntity requestVacationEntity;
    private RequestVacationDto requestVacationDto;
    private RequestVacationEntity requestVacationEntityTwo;
    private RequestVacationDto requestVacationDtoTwo;
    @InjectMocks
    private RequestVacationServiceImpl requestVacationService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        employeeEntity = createEmployeeEntity(1L, "Leo", "Messi", "123456", "366468383", "Argentina",
                LocalDate.parse("2023-01-05"), TypeOfContract.INDEFINITE_TERM, UserStatus.ACTIVE);
        employeeDto = createEmployeeDto(1L, "Leo", "Messi", "123456", "366468383", "Argentina",
                LocalDate.parse("2023-01-05"), TypeOfContract.INDEFINITE_TERM, UserStatus.ACTIVE);

        employeeEntityTwo = createEmployeeEntity(2L, "CR7", "Ronaldo", "1234567", "366468383", "Portugal",
                LocalDate.parse("2023-08-11"), TypeOfContract.INDEFINITE_TERM, UserStatus.ACTIVE);
        employeeDtoTwo = createEmployeeDto(2L, "CR7", "Ronaldo", "1234567", "366468383", "Portugal",
                LocalDate.parse("2023-08-11"), TypeOfContract.INDEFINITE_TERM, UserStatus.ACTIVE);

        userEntity = createUserEntity(1L, "leo", "123", "leo@gmail.com", UserStatus.ACTIVE, employeeEntity);
        userDto = createUserDto(1L, "leo", "123", "leo@gmail.com", UserStatus.ACTIVE);

        userEntityTwo = createUserEntity(2L, "cr7", "123", "cr7@gmail.com", UserStatus.ACTIVE, employeeEntityTwo);
        userDtoTwo = createUserDto(2L, "cr7", "123", "cr7@gmail.com", UserStatus.ACTIVE);

        requestVacationEntity = createRequestVacationEntity(1L, "First Request", "description", LocalDate.parse("2024-05-14"),6 ,new UserEntity());
        requestVacationDto = createRequestVacationDto(1L, "First Request", "description", LocalDate.parse("2024-05-14"),6 );

        requestVacationEntityTwo = createRequestVacationEntityTwo(2L, "Second Request", "description", LocalDate.parse("2024-05-14"),new UserEntity());
        requestVacationDtoTwo = createRequestVacationDtoTwo(2L, "Second Request", "description", LocalDate.parse("2024-05-14"));




    }

    @DisplayName("Create Request Vacation One Year")
    @Test
    public void testCreateRequestVacation() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(userEntity));
        when(requestVacationRepository.save(Mockito.any(RequestVacationEntity.class))).thenReturn(requestVacationEntity);
        when(requestVacationMapper.requestVacationToRequestVacationDto(Mockito.any())).thenReturn(requestVacationDto);


        assertThat(requestVacationDto.getFeStartDate()).isNotNull();

        RequestVacationDto requestVacation = requestVacationService.createRequestVacation(requestVacationDto, userDto.getNmIdUser());
        assertThat(requestVacation).isNotNull();
    }

    @DisplayName("Create Request Vacation Greater two months ")
    @Test
    public void testCreateRequestVacationMonth() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(userEntityTwo));
        when(requestVacationRepository.save(Mockito.any(RequestVacationEntity.class))).thenReturn(requestVacationEntityTwo);
        when(requestVacationMapper.requestVacationToRequestVacationDto(Mockito.any())).thenReturn(requestVacationDtoTwo);


        assertThat(requestVacationDtoTwo.getFeStartDate()).isNotNull();

        RequestVacationDto requestVacation = requestVacationService.createRequestVacation(requestVacationDtoTwo, userDtoTwo.getNmIdUser());
        assertThat(requestVacation).isNotNull();
    }

    @DisplayName("Get all requests By User")
    @Test
    public void testGetAllRequestByUser(){
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(userEntity));
        List<RequestVacationEntity> requestEntities = Arrays.asList(requestVacationEntity);
        when(requestVacationRepository.findAllRequestByUser(Mockito.anyLong())).thenReturn(requestEntities);
        when(requestVacationMapper.requestVacationToRequestVacationDto(Mockito.any())).thenReturn(requestVacationDto);

        List<RequestVacationDto> requestVacation = requestVacationService.getAllRequestVacation(userDto.getNmIdUser());
        assertThat(requestVacation).isNotNull();
    }

    @DisplayName("Get requests By User and By request")
    @Test
    public void testGetRequestByUserAndByRequest(){
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(userEntity));
        List<RequestVacationEntity> requestEntities = Arrays.asList(requestVacationEntity);
        when(requestVacationRepository.findByNmIdRequestAndNmIdUser(Mockito.anyLong(),Mockito.anyLong())).thenReturn(requestEntities);
        when(requestVacationMapper.requestVacationToRequestVacationDto(Mockito.any())).thenReturn(requestVacationDto);

        List<RequestVacationDto> requestVacation = requestVacationService.getRequestVacationById(requestVacationDto.getNmIdRequest(),userDto.getNmIdUser());
        assertThat(requestVacation).isNotNull();
    }


    private RequestVacationEntity createRequestVacationEntity(Long id, String nameRequest, String description, LocalDate startDate, int numberDays, UserEntity user) {
        return RequestVacationEntity.builder()
                .nmIdRequest(id)
                .nameRequest(nameRequest)
                .description(description)
                .feStartDate(startDate)
                .nmNumberOfDaysRequested(numberDays)
                .userEntity(user)
                .build();
    }

    private RequestVacationDto createRequestVacationDto(Long id, String nameRequest, String description, LocalDate startDate, int numberDays) {
        return RequestVacationDto.builder()
                .nmIdRequest(id)
                .nameRequest(nameRequest)
                .description(description)
                .feStartDate(startDate)
                .nmNumberOfDaysRequested(numberDays)
                .build();
    }

    private RequestVacationEntity createRequestVacationEntityTwo(Long id, String nameRequest, String description, LocalDate startDate,UserEntity user) {
        return RequestVacationEntity.builder()
                .nmIdRequest(id)
                .nameRequest(nameRequest)
                .description(description)
                .feStartDate(startDate)
                .userEntity(user)
                .build();
    }

    private RequestVacationDto createRequestVacationDtoTwo(Long id, String nameRequest, String description, LocalDate startDate) {
        return RequestVacationDto.builder()
                .nmIdRequest(id)
                .nameRequest(nameRequest)
                .description(description)
                .feStartDate(startDate)
                .build();
    }


    private UserEntity createUserEntity(Long id, String username, String password, String email, UserStatus userStatus, EmployeeEntity employee) {
        return UserEntity.builder()
                .nmIdUser(id)
                .dsUsername(username)
                .dsPassword(password)
                .dsEmail(email)
                .dsUserStatus(userStatus)
                .employeeEntity(employee)
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
