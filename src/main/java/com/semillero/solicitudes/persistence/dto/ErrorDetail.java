package com.semillero.solicitudes.persistence.dto;

import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class ErrorDetail {
    private String title;
    private int status;
    private String detail;
    private Date dateTime;
    private String developerMessage;
}
