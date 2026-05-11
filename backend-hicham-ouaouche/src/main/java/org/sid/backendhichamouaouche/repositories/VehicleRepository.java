package org.sid.backendhichamouaouche.repositories;

import org.sid.backendhichamouaouche.entities.Vehicle;
import org.sid.backendhichamouaouche.enums.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    List<Vehicle> findByAgencyId(Long agencyId);

    List<Vehicle> findByStatus(VehicleStatus status);
}