package com.semillero.solicitudes.persistence.dto;

import com.semillero.solicitudes.persistence.enums.StatusResource;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private Long nmIdUser;
    private String dsUsername;
    private String dsPassword;
    private String dsEmail;
    private StatusResource dsUserStatus;
    private LocalDateTime feUserCreated;
}
