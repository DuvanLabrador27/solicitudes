package com.semillero.solicitudes.persistence.enums;

public enum UserStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private String description;

    private UserStatus(String description){
        this.description = description;
    }

}
