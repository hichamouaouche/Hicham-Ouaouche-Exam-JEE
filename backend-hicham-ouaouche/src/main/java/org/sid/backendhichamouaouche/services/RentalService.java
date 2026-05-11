package org.sid.backendhichamouaouche.services;

import org.sid.backendhichamouaouche.dtos.RentalDto;
import org.sid.backendhichamouaouche.dtos.RentalRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RentalService {

    List<RentalDto> findAll();

    RentalDto findById(Long id);

    List<RentalDto> findByVehicleId(String vehicleId);

    Page<RentalDto> historyByVehicleId(String vehicleId, Pageable pageable);

    RentalDto create(RentalRequest request);

    RentalDto update(Long id, RentalRequest request);

    void delete(Long id);
}