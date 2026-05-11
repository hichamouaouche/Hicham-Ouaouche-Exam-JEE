package org.sid.backendhichamouaouche.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ROLE_CLIENT,
    ROLE_EMPLOYEE,
    ROLE_ADMIN;

    @JsonCreator
    public static Role fromValue(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim().toUpperCase().replace(" ", "_");
        for (Role role : values()) {
            if (role.name().equals(normalized)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}