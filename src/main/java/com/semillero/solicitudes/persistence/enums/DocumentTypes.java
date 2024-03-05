package com.semillero.solicitudes.persistence.enums;

public enum DocumentTypes {
    CC("CC"),
    CE("CE "),
    PA("Passport");

    private String description;

    private DocumentTypes(String description){
        this.description = description;
    }
}
