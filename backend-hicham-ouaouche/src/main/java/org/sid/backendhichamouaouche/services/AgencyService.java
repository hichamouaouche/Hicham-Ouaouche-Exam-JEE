package org.sid.backendhichamouaouche.services;

import org.sid.backendhichamouaouche.dtos.AgencyDto;
import org.sid.backendhichamouaouche.dtos.AgencyRequest;

import java.util.List;

public interface AgencyService {

    List<AgencyDto> findAll();

    AgencyDto findById(Long id);

    AgencyDto create(AgencyRequest request);

    AgencyDto update(Long id, AgencyRequest request);

    void delete(Long id);
}