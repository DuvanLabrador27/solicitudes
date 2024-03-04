package com.semillero.solicitudes.utils;

public enum StatusRequestVacation {
    APPROVED("Approve"),
    PENDING("Pending"),
    REJECTED("Rejected");

    private String description;

    private StatusRequestVacation(String description){
        this.description = description;
    }
}
