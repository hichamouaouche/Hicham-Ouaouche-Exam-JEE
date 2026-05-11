package org.sid.backendhichamouaouche.services.impl;

import org.sid.backendhichamouaouche.dtos.AgencyDto;
import org.sid.backendhichamouaouche.dtos.AgencyRequest;
import org.sid.backendhichamouaouche.entities.Agency;
import org.sid.backendhichamouaouche.exceptions.BusinessException;
import org.sid.backendhichamouaouche.exceptions.ResourceNotFoundException;
import org.sid.backendhichamouaouche.mappers.AgencyMapper;
import org.sid.backendhichamouaouche.repositories.AgencyRepository;
import org.sid.backendhichamouaouche.repositories.VehicleRepository;
import org.sid.backendhichamouaouche.services.AgencyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AgencyServiceImpl implements AgencyService {

    private final AgencyRepository agencyRepository;
    private final VehicleRepository vehicleRepository;

    public AgencyServiceImpl(AgencyRepository agencyRepository, VehicleRepository vehicleRepository) {
        this.agencyRepository = agencyRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgencyDto> findAll() {
        return agencyRepository.findAll().stream().map(AgencyMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AgencyDto findById(Long id) {
        return AgencyMapper.toDto(getAgency(id));
    }

    @Override
    public AgencyDto create(AgencyRequest request) {
        Agency agency = AgencyMapper.toEntity(request);
        return AgencyMapper.toDto(agencyRepository.save(agency));
    }

    @Override
    public AgencyDto update(Long id, AgencyRequest request) {
        Agency agency = getAgency(id);
        AgencyMapper.updateEntity(agency, request);
        return AgencyMapper.toDto(agencyRepository.save(agency));
    }

    @Override
    public void delete(Long id) {
        if (!vehicleRepository.findByAgencyId(id).isEmpty()) {
            throw new BusinessException("Cannot delete agency with assigned vehicles");
        }
        if (!agencyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agency not found: " + id);
        }
        agencyRepository.deleteById(id);
    }

    private Agency getAgency(Long id) {
        return agencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found: " + id));
    }
}