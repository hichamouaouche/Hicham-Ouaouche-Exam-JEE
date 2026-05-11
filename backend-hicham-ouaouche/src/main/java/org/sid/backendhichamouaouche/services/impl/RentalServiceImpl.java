package org.sid.backendhichamouaouche.services.impl;

import org.sid.backendhichamouaouche.dtos.RentalDto;
import org.sid.backendhichamouaouche.dtos.RentalRequest;
import org.sid.backendhichamouaouche.entities.Rental;
import org.sid.backendhichamouaouche.entities.Vehicle;
import org.sid.backendhichamouaouche.enums.VehicleStatus;
import org.sid.backendhichamouaouche.exceptions.BusinessException;
import org.sid.backendhichamouaouche.exceptions.ResourceNotFoundException;
import org.sid.backendhichamouaouche.mappers.RentalMapper;
import org.sid.backendhichamouaouche.repositories.RentalRepository;
import org.sid.backendhichamouaouche.repositories.VehicleRepository;
import org.sid.backendhichamouaouche.services.RentalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;

    public RentalServiceImpl(RentalRepository rentalRepository, VehicleRepository vehicleRepository) {
        this.rentalRepository = rentalRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalDto> findAll() {
        return rentalRepository.findAll().stream().map(RentalMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RentalDto findById(Long id) {
        return RentalMapper.toDto(getRental(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalDto> findByVehicleId(String vehicleId) {
        return rentalRepository.findByVehicleId(vehicleId).stream().map(RentalMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RentalDto> historyByVehicleId(String vehicleId, Pageable pageable) {
        return rentalRepository.findByVehicleIdOrderByStartDateDesc(vehicleId, pageable).map(RentalMapper::toDto);
    }

    @Override
    public RentalDto create(RentalRequest request) {
        Vehicle vehicle = getVehicle(request.getVehicleId());
        ensureAvailable(vehicle);
        Rental rental = RentalMapper.toEntity(request, vehicle);
        applyPrice(rental, vehicle);
        Rental saved = rentalRepository.save(rental);
        vehicle.setStatus(VehicleStatus.LOUE);
        vehicleRepository.save(vehicle);
        return RentalMapper.toDto(saved);
    }

    @Override
    public RentalDto update(Long id, RentalRequest request) {
        Rental rental = getRental(id);
        Vehicle newVehicle = getVehicle(request.getVehicleId());
        if (!rental.getVehicle().getId().equals(newVehicle.getId())) {
            ensureAvailable(newVehicle);
        }
        Vehicle oldVehicle = rental.getVehicle();
        RentalMapper.updateEntity(rental, request, newVehicle);
        applyPrice(rental, newVehicle);
        Rental saved = rentalRepository.save(rental);
        if (!oldVehicle.getId().equals(newVehicle.getId())) {
            releaseIfNecessary(oldVehicle);
            newVehicle.setStatus(VehicleStatus.LOUE);
            vehicleRepository.save(newVehicle);
        }
        return RentalMapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        Rental rental = getRental(id);
        Vehicle vehicle = rental.getVehicle();
        rentalRepository.delete(rental);
        releaseIfNecessary(vehicle);
    }

    private void applyPrice(Rental rental, Vehicle vehicle) {
        long days = ChronoUnit.DAYS.between(rental.getStartDate(), rental.getEndDate());
        if (days <= 0) {
            throw new BusinessException("Rental end date must be after start date");
        }
        rental.setTotalPrice(days * vehicle.getPrixParJour());
    }

    private void ensureAvailable(Vehicle vehicle) {
        if (vehicle.getStatus() != VehicleStatus.DISPONIBLE) {
            throw new BusinessException("Vehicle is not available for rental");
        }
    }

    private void releaseIfNecessary(Vehicle vehicle) {
        if (vehicle.getStatus() == VehicleStatus.LOUE) {
            vehicle.setStatus(VehicleStatus.DISPONIBLE);
            vehicleRepository.save(vehicle);
        }
    }

    private Vehicle getVehicle(String id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));
    }

    private Rental getRental(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found: " + id));
    }
}