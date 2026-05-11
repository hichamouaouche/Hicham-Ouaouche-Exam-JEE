package org.sid.backendhichamouaouche.web;

import org.sid.backendhichamouaouche.dtos.RentalDto;
import org.sid.backendhichamouaouche.dtos.RentalRequest;
import org.sid.backendhichamouaouche.services.RentalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','CLIENT')")
    public ResponseEntity<List<RentalDto>> findAll() {
        return ResponseEntity.ok(rentalService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','CLIENT')")
    public ResponseEntity<RentalDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.findById(id));
    }

    @GetMapping("/vehicles/{vehicleId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','CLIENT')")
    public ResponseEntity<List<RentalDto>> findByVehicle(@PathVariable String vehicleId) {
        return ResponseEntity.ok(rentalService.findByVehicleId(vehicleId));
    }

    @GetMapping("/history")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','CLIENT')")
    public ResponseEntity<Page<RentalDto>> history(@RequestParam String vehicleId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(rentalService.historyByVehicleId(vehicleId, pageable));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','CLIENT')")
    public ResponseEntity<RentalDto> create(@Validated @RequestBody RentalRequest request) {
        return ResponseEntity.ok(rentalService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<RentalDto> update(@PathVariable Long id, @Validated @RequestBody RentalRequest request) {
        return ResponseEntity.ok(rentalService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rentalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}