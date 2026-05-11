package org.sid.backendhichamouaouche.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VehicleStatus {
    DISPONIBLE,
    LOUE,
    EN_MAINTENANCE;

    @JsonCreator
    public static VehicleStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        String normalized = normalize(value);
        for (VehicleStatus status : values()) {
            if (normalize(status.name()).equals(normalized)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown vehicle status: " + value);
    }

    private static String normalize(String value) {
        return value.trim().toUpperCase().replace("_", "").replace(" ", "");
    }
}