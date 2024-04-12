package com.semillero.solicitudes.employees.controllerEmployeeTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.semillero.solicitudes.persistence.dto.EmployeeDto;
import com.semillero.solicitudes.persistence.enums.DocumentTypes;
import com.semillero.solicitudes.persistence.enums.TypeOfContract;
import com.semillero.solicitudes.persistence.enums.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@WebAppConfiguration
public class ControllerEmployeeTest {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("Get all employees")
    @Test
    public void testGetAllEmployees() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/employeeList")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @DisplayName("Create employee")
    @Test
    @Transactional
    public void testCreateEmployee() throws Exception {
        EmployeeDto employeeDto = createEmployeeDto(25L, "Leo", "Messi", "123456", "366468383", "Argentina",
                LocalDate.parse("2023-01-05"), TypeOfContract.INDEFINITE_TERM, UserStatus.ACTIVE);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees/employeeCreate")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(employeeDto)))
                .andReturn();
        assertEquals(201, result.getResponse().getStatus());
    }

    private String mapToJson(Object object) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        return mapper.writeValueAsString(object);
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
