package org.sid.backendhichamouaouche.repositories;

import org.sid.backendhichamouaouche.entities.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByVehicleId(String vehicleId);

    Page<Rental> findByVehicleIdOrderByStartDateDesc(String vehicleId, Pageable pageable);
}