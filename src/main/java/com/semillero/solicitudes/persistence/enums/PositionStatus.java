package com.semillero.solicitudes.persistence.enums;

public enum PositionStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private String description;

    private PositionStatus(String description){
        this.description = description;
    }

}
