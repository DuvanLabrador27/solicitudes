package com.semillero.solicitudes.persistence.dto;

import com.semillero.solicitudes.persistence.enums.DocumentTypes;
import com.semillero.solicitudes.persistence.enums.UserStatus;
import com.semillero.solicitudes.persistence.enums.TypeOfContract;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDto {
    private Long nmIdEmployee;
    private String dsDocument;
    private DocumentTypes dsDocumentType;
    private String dsName;
    private String dsLastname;
    private String dsPhoneNumber;
    private String dsAddress;
    private LocalDate feHireDate;
    private LocalDate feDepartureDate;
    private TypeOfContract dsTypeOfContract;
    private UserStatus dsEmployeeStatus;
}
