package org.sid.backendhichamouaouche.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum FuelType {
    ESSENCE,
    DIESEL,
    HYBRIDE,
    ELECTRIQUE;

    @JsonCreator
    public static FuelType fromValue(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim().toUpperCase();
        for (FuelType type : values()) {
            if (type.name().equals(normalized)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown fuel type: " + value);
    }
}