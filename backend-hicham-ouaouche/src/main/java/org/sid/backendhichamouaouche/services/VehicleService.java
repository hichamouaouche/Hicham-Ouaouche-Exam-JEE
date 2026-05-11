package org.sid.backendhichamouaouche.services;

import org.sid.backendhichamouaouche.dtos.VehicleDto;
import org.sid.backendhichamouaouche.dtos.VehicleRequest;

import java.util.List;

public interface VehicleService {

    List<VehicleDto> findAll();

    VehicleDto findById(String id);

    List<VehicleDto> findByAgencyId(Long agencyId);

    List<VehicleDto> search(String status, String type);

    VehicleDto create(VehicleRequest request);

    VehicleDto update(String id, VehicleRequest request);

    void delete(String id);
}