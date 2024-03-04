package com.semillero.solicitudes.utils;

public enum StatusUser {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private String description;

    private StatusUser(String description){
        this.description = description;
    }

}
