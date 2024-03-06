package com.semillero.solicitudes.persistence.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorDetail {
    private String title;
    private int status;
    private String detail;
    private Date dateTime;
    private String developerMessage;
}
