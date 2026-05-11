package org.sid.backendhichamouaouche.web;

import org.sid.backendhichamouaouche.dtos.AgencyDto;
import org.sid.backendhichamouaouche.dtos.AgencyRequest;
import org.sid.backendhichamouaouche.dtos.VehicleDto;
import org.sid.backendhichamouaouche.services.AgencyService;
import org.sid.backendhichamouaouche.services.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/agencies")
public class AgencyController {

    private final AgencyService agencyService;
    private final VehicleService vehicleService;

    public AgencyController(AgencyService agencyService, VehicleService vehicleService) {
        this.agencyService = agencyService;
        this.vehicleService = vehicleService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','CLIENT')")
    public ResponseEntity<List<AgencyDto>> findAll() {
        return ResponseEntity.ok(agencyService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','CLIENT')")
    public ResponseEntity<AgencyDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(agencyService.findById(id));
    }

    @GetMapping("/{agencyId}/vehicles")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','CLIENT')")
    public ResponseEntity<List<VehicleDto>> vehiclesByAgency(@PathVariable Long agencyId) {
        return ResponseEntity.ok(vehicleService.findByAgencyId(agencyId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AgencyDto> create(@Validated @RequestBody AgencyRequest request) {
        return ResponseEntity.ok(agencyService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AgencyDto> update(@PathVariable Long id, @Validated @RequestBody AgencyRequest request) {
        return ResponseEntity.ok(agencyService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        agencyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}