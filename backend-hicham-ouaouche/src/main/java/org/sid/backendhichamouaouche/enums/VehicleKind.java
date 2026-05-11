package org.sid.backendhichamouaouche.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VehicleKind {
    CAR,
    MOTO;

    @JsonCreator
    public static VehicleKind fromValue(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim().toUpperCase();
        if (normalized.equals("CAR") || normalized.equals("VOITURE")) {
            return CAR;
        }
        if (normalized.equals("MOTO")) {
            return MOTO;
        }
        throw new IllegalArgumentException("Unknown vehicle kind: " + value);
    }
}