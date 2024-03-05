package com.semillero.solicitudes.persistence.enums;

public enum TypeOfContract {
    FIXED_TERM("Fixed term"),
    INDEFINITE_TERM("Indefinite term"),
    OPS("OPS"),
    APPRENTICESHIP("Apprenticeship"),;

    private String description;

    private TypeOfContract(String description){
        this.description = description;
    }
}
