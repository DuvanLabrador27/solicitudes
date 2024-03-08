package com.semillero.solicitudes.persistence.dto;

import com.semillero.solicitudes.persistence.enums.StatusRequestVacation;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestVacationDto {
    private Long nmIdRequest;
    private String nameRequest;
    private String description;
    private Integer nmNumberOfDaysRequested;
    private LocalDate feStartDate;
    private LocalDate feEndDate;
    private LocalDate feReinstatementDate;
    private StatusRequestVacation dsStatus;
    private LocalDateTime feRequestCreation;


}
