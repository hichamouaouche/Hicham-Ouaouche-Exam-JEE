package org.sid.backendhichamouaouche.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MotoType {
    SPORTIVE,
    SCOOTER,
    ROADSTER,
    TOURING;

    @JsonCreator
    public static MotoType fromValue(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim().toUpperCase();
        for (MotoType type : values()) {
            if (type.name().equals(normalized)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown moto type: " + value);
    }
}