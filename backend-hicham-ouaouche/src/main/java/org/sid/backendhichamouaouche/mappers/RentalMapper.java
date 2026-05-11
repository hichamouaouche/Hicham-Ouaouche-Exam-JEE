package org.sid.backendhichamouaouche.mappers;

import org.sid.backendhichamouaouche.dtos.RentalDto;
import org.sid.backendhichamouaouche.dtos.RentalRequest;
import org.sid.backendhichamouaouche.entities.Rental;
import org.sid.backendhichamouaouche.entities.Vehicle;

public final class RentalMapper {

    private RentalMapper() {
    }

    public static RentalDto toDto(Rental rental) {
        String label = rental.getVehicle() == null ? null : rental.getVehicle().getMarque() + " " + rental.getVehicle().getModele();
        return RentalDto.builder()
                .id(rental.getId())
                .vehicleId(rental.getVehicle() == null ? null : rental.getVehicle().getId())
                .vehicleLabel(label)
                .customerName(rental.getCustomerName())
                .customerPhone(rental.getCustomerPhone())
                .startDate(rental.getStartDate())
                .endDate(rental.getEndDate())
                .totalPrice(rental.getTotalPrice())
                .createdAt(rental.getCreatedAt())
                .build();
    }

    public static Rental toEntity(RentalRequest request, Vehicle vehicle) {
        return Rental.builder()
                .vehicle(vehicle)
                .customerName(request.getCustomerName())
                .customerPhone(request.getCustomerPhone())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }

    public static void updateEntity(Rental rental, RentalRequest request, Vehicle vehicle) {
        rental.setVehicle(vehicle);
        rental.setCustomerName(request.getCustomerName());
        rental.setCustomerPhone(request.getCustomerPhone());
        rental.setStartDate(request.getStartDate());
        rental.setEndDate(request.getEndDate());
    }
}