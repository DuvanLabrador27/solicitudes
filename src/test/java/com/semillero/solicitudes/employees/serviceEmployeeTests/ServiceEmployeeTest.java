package com.semillero.solicitudes.employees.serviceEmployeeTests;

import com.semillero.solicitudes.exceptions.ResourceNotFoundException;
import com.semillero.solicitudes.persistence.dto.EmployeeDto;
import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import com.semillero.solicitudes.persistence.enums.DocumentTypes;
import com.semillero.solicitudes.persistence.enums.TypeOfContract;
import com.semillero.solicitudes.persistence.enums.UserStatus;
import com.semillero.solicitudes.persistence.mappers.EmployeeMapper;
import com.semillero.solicitudes.persistence.repositories.EmployeeRepository;
import com.semillero.solicitudes.services.impl.EmployeeServiceImpl;
import com.semillero.solicitudes.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class ServiceEmployeeTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeEntity employeeEntity;
    private EmployeeDto employeeDto;
    private EmployeeEntity employeeEntityTwo;
    private EmployeeDto employeeDtoTwo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        employeeService = new EmployeeServiceImpl(employeeRepository, employeeMapper);

        employeeEntity = createEmployeeEntity(1L, "Leo", "Messi", "123456", "366468383", "Argentina",
                LocalDate.parse("2023-01-05"), TypeOfContract.INDEFINITE_TERM, UserStatus.ACTIVE);
        employeeDto = createEmployeeDto(1L, "Leo", "Messi", "123456", "366468383", "Argentina",
                LocalDate.parse("2023-01-05"), TypeOfContract.INDEFINITE_TERM, UserStatus.ACTIVE);

        employeeEntityTwo = createEmployeeEntity(2L, "CR7", "Ronaldo", "1234567", "366468383", "Portugal",
                LocalDate.parse("2023-01-05"), TypeOfContract.INDEFINITE_TERM, UserStatus.ACTIVE);
        employeeDtoTwo = createEmployeeDto(2L, "CR7", "Ronaldo", "1234567", "366468383", "Portugal",
                LocalDate.parse("2023-01-05"), TypeOfContract.INDEFINITE_TERM, UserStatus.ACTIVE);


    }

    @DisplayName("Create Employee")
    @Test
    public void testCreateEmployees() {

        when(employeeRepository.save(Mockito.any(EmployeeEntity.class))).thenReturn(employeeEntity);
        when(employeeMapper.employeeToEmployeeDto(Mockito.any())).thenReturn(employeeDto);

        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        assertThat(savedEmployee).isNotNull();
    }




    @DisplayName("List All Employees")
    @Test
    public void testGetAllEmployees() {
        List<EmployeeEntity> employeeEntities = Arrays.asList(employeeEntity,employeeEntityTwo);
        when(employeeRepository.findAll()).thenReturn(employeeEntities);
        when(employeeMapper.employeeToEmployeeDto(Mockito.any())).thenReturn(employeeDto,employeeDtoTwo);

        List<EmployeeDto> employeeList = employeeService.getEmployees();
        employeeList.stream().forEach(employees -> System.out.println(employees));
        assertThat(employeeList).isNotNull();

    }

    @DisplayName("Get Employee By Id")
    @Test
    public void testGetEmployeeById(){
        Mockito.when(employeeRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(employeeEntity));
        when(employeeMapper.employeeToEmployeeDto(Mockito.any())).thenReturn(employeeDto);
        EmployeeDto resultOptional = employeeService.getEmployeeById(employeeDto.getNmIdEmployee());
        assertThat(resultOptional).isNotNull();
    }


    @DisplayName("Update Employee")
    @Test
    public void testUpdateEmployee(){
        Mockito.when(employeeRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(employeeEntity));
        when(employeeRepository.save(Mockito.any(EmployeeEntity.class)))
                .thenReturn(employeeEntity);
        when(employeeMapper.employeeToEmployeeDto(Mockito.any())).thenReturn(employeeDto);

        EmployeeDto employee = employeeService.updateEmployee(employeeDto.getNmIdEmployee(), employeeDto);
        assertThat(employee).isNotNull();
    }

    @DisplayName("Delete Employee")
    @Test
    public void testDeleteEmployee(){
        Mockito.when(employeeRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(employeeEntity));
        Mockito.doNothing().when(employeeRepository).deleteById(Mockito.anyLong());
        boolean result = employeeService.deleteEmployee(employeeEntity.getNmIdEmployee());
        assertThat(result).isTrue();
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

