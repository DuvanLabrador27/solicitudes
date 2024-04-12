package com.semillero.solicitudes.persistence.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Builder
public class RequestVacationDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long nmIdRequest;
    private String nameRequest;
    private String description;
    private Integer nmNumberOfDaysRequested;
    private LocalDate feStartDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate feEndDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate feReinstatementDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StatusRequestVacation dsStatus;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime feRequestCreation;
}
