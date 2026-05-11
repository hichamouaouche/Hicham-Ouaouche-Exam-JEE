package org.sid.backendhichamouaouche.services.impl;

import org.sid.backendhichamouaouche.dtos.VehicleDto;
import org.sid.backendhichamouaouche.dtos.VehicleRequest;
import org.sid.backendhichamouaouche.entities.Agency;
import org.sid.backendhichamouaouche.entities.Car;
import org.sid.backendhichamouaouche.entities.Moto;
import org.sid.backendhichamouaouche.entities.Vehicle;
import org.sid.backendhichamouaouche.enums.VehicleKind;
import org.sid.backendhichamouaouche.enums.VehicleStatus;
import org.sid.backendhichamouaouche.exceptions.BusinessException;
import org.sid.backendhichamouaouche.exceptions.ResourceNotFoundException;
import org.sid.backendhichamouaouche.mappers.VehicleMapper;
import org.sid.backendhichamouaouche.repositories.AgencyRepository;
import org.sid.backendhichamouaouche.repositories.RentalRepository;
import org.sid.backendhichamouaouche.repositories.VehicleRepository;
import org.sid.backendhichamouaouche.services.VehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final AgencyRepository agencyRepository;
    private final RentalRepository rentalRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, AgencyRepository agencyRepository, RentalRepository rentalRepository) {
        this.vehicleRepository = vehicleRepository;
        this.agencyRepository = agencyRepository;
        this.rentalRepository = rentalRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDto> findAll() {
        return vehicleRepository.findAll().stream().map(VehicleMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleDto findById(String id) {
        return VehicleMapper.toDto(getVehicle(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDto> findByAgencyId(Long agencyId) {
        return vehicleRepository.findByAgencyId(agencyId).stream().map(VehicleMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDto> search(String status, String type) {
        VehicleStatus parsedStatus = status == null || status.isBlank() ? null : VehicleStatus.fromValue(status);
        VehicleKind kind = type == null || type.isBlank() ? null : VehicleKind.fromValue(type);
        return vehicleRepository.findAll().stream()
                .filter(vehicle -> parsedStatus == null || vehicle.getStatus() == parsedStatus)
                .filter(vehicle -> kind == null || matchesKind(vehicle, kind))
                .map(VehicleMapper::toDto)
                .toList();
    }

    @Override
    public VehicleDto create(VehicleRequest request) {
        Agency agency = getAgency(request.getAgencyId());
        Vehicle vehicle = VehicleMapper.toEntity(request, agency);
        validateSpecificFields(vehicle, request);
        Vehicle saved = vehicleRepository.save(vehicle);
        return VehicleMapper.toDto(saved);
    }

    @Override
    public VehicleDto update(String id, VehicleRequest request) {
        Vehicle vehicle = getVehicle(id);
        VehicleKind currentKind = vehicle instanceof Car ? VehicleKind.CAR : VehicleKind.MOTO;
        VehicleKind requestedKind = VehicleKind.fromValue(request.getVehicleType());
        if (currentKind != requestedKind) {
            throw new BusinessException("Vehicle type cannot be changed during update");
        }
        Agency agency = getAgency(request.getAgencyId());
        validateSpecificFields(vehicle, request);
        VehicleMapper.updateEntity(vehicle, request, agency);
        return VehicleMapper.toDto(vehicleRepository.save(vehicle));
    }

    @Override
    public void delete(String id) {
        if (!rentalRepository.findByVehicleId(id).isEmpty()) {
            throw new BusinessException("Cannot delete vehicle with rental history");
        }
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found: " + id);
        }
        vehicleRepository.deleteById(id);
    }

    private Agency getAgency(Long id) {
        return agencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found: " + id));
    }

    private Vehicle getVehicle(String id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));
    }

    private boolean matchesKind(Vehicle vehicle, VehicleKind kind) {
        return (kind == VehicleKind.CAR && vehicle instanceof Car) || (kind == VehicleKind.MOTO && vehicle instanceof Moto);
    }

    private void validateSpecificFields(Vehicle vehicle, VehicleRequest request) {
        VehicleKind kind = vehicle instanceof Car ? VehicleKind.CAR : VehicleKind.MOTO;
        if (kind == VehicleKind.CAR) {
            if (request.getNombrePortes() == null || request.getTypeCarburant() == null || request.getBoiteVitesse() == null) {
                throw new BusinessException("Car requires nombrePortes, typeCarburant and boiteVitesse");
            }
        } else {
            if (request.getCylindree() == null || request.getTypeMoto() == null || request.getCasqueInclus() == null) {
                throw new BusinessException("Moto requires cylindree, typeMoto and casqueInclus");
            }
        }
    }
}