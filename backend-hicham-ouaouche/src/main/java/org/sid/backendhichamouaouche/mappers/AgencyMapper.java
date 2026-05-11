package org.sid.backendhichamouaouche.mappers;

import org.sid.backendhichamouaouche.dtos.AgencyDto;
import org.sid.backendhichamouaouche.dtos.AgencyRequest;
import org.sid.backendhichamouaouche.entities.Agency;

public final class AgencyMapper {

    private AgencyMapper() {
    }

    public static AgencyDto toDto(Agency agency) {
        return AgencyDto.builder()
                .id(agency.getId())
                .name(agency.getName())
                .address(agency.getAddress())
                .city(agency.getCity())
                .phone(agency.getPhone())
                .vehicleCount(agency.getVehicles() == null ? 0 : agency.getVehicles().size())
                .build();
    }

    public static Agency toEntity(AgencyRequest request) {
        return Agency.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .phone(request.getPhone())
                .build();
    }

    public static void updateEntity(Agency agency, AgencyRequest request) {
        agency.setName(request.getName());
        agency.setAddress(request.getAddress());
        agency.setCity(request.getCity());
        agency.setPhone(request.getPhone());
    }
}