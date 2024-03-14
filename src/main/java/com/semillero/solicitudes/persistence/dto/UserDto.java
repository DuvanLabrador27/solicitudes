package com.semillero.solicitudes.persistence.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.semillero.solicitudes.persistence.enums.UserStatus;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long nmIdUser;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String dsUsername;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String dsPassword;
    private String dsEmail;
    private UserStatus dsUserStatus;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime feUserCreated;
}
