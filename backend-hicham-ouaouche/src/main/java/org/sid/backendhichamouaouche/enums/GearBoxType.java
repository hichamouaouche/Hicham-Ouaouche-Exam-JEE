package org.sid.backendhichamouaouche.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GearBoxType {
    MANUELLE,
    AUTOMATIQUE;

    @JsonCreator
    public static GearBoxType fromValue(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim().toUpperCase();
        for (GearBoxType type : values()) {
            if (type.name().equals(normalized)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown gearbox type: " + value);
    }
}