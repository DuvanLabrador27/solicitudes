package com.semillero.solicitudes.persistence.enums;

public enum StatusResource {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private String description;

    private StatusResource(String description){
        this.description = description;
    }

}
