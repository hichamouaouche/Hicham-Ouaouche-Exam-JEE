package org.sid.backendhichamouaouche.mappers;

import org.sid.backendhichamouaouche.dtos.VehicleDto;
import org.sid.backendhichamouaouche.dtos.VehicleRequest;
import org.sid.backendhichamouaouche.entities.Agency;
import org.sid.backendhichamouaouche.entities.Car;
import org.sid.backendhichamouaouche.entities.Moto;
import org.sid.backendhichamouaouche.entities.Vehicle;
import org.sid.backendhichamouaouche.enums.FuelType;
import org.sid.backendhichamouaouche.enums.GearBoxType;
import org.sid.backendhichamouaouche.enums.MotoType;
import org.sid.backendhichamouaouche.enums.VehicleKind;

public final class VehicleMapper {

    private VehicleMapper() {
    }

    public static VehicleDto toDto(Vehicle vehicle) {
        VehicleDto.VehicleDtoBuilder builder = VehicleDto.builder()
                .id(vehicle.getId())
                .vehicleType(vehicle instanceof Car ? VehicleKind.CAR.name() : VehicleKind.MOTO.name())
                .marque(vehicle.getMarque())
                .modele(vehicle.getModele())
                .matricule(vehicle.getMatricule())
                .prixParJour(vehicle.getPrixParJour())
                .dateMiseEnService(vehicle.getDateMiseEnService())
                .status(vehicle.getStatus())
                .agencyId(vehicle.getAgency() == null ? null : vehicle.getAgency().getId())
                .agencyName(vehicle.getAgency() == null ? null : vehicle.getAgency().getName())
                .rentalCount(vehicle.getRentals() == null ? 0 : vehicle.getRentals().size());

        if (vehicle instanceof Car car) {
            builder.nombrePortes(car.getNombrePortes())
                    .typeCarburant(car.getTypeCarburant() == null ? null : car.getTypeCarburant().name())
                    .boiteVitesse(car.getBoiteVitesse() == null ? null : car.getBoiteVitesse().name());
        }
        if (vehicle instanceof Moto moto) {
            builder.cylindree(moto.getCylindree())
                    .typeMoto(moto.getTypeMoto() == null ? null : moto.getTypeMoto().name())
                    .casqueInclus(moto.isCasqueInclus());
        }
        return builder.build();
    }

    public static Vehicle toEntity(VehicleRequest request, Agency agency) {
        VehicleKind kind = VehicleKind.fromValue(request.getVehicleType());
        if (kind == VehicleKind.CAR) {
            return Car.builder()
                    .agency(agency)
                    .marque(request.getMarque())
                    .modele(request.getModele())
                    .matricule(request.getMatricule())
                    .prixParJour(request.getPrixParJour())
                    .dateMiseEnService(request.getDateMiseEnService())
                    .status(request.getStatus())
                    .nombrePortes(request.getNombrePortes() == null ? 0 : request.getNombrePortes())
                    .typeCarburant(request.getTypeCarburant() == null ? null : FuelType.fromValue(request.getTypeCarburant()))
                    .boiteVitesse(request.getBoiteVitesse() == null ? null : GearBoxType.fromValue(request.getBoiteVitesse()))
                    .build();
        }
        return Moto.builder()
                .agency(agency)
                .marque(request.getMarque())
                .modele(request.getModele())
                .matricule(request.getMatricule())
                .prixParJour(request.getPrixParJour())
                .dateMiseEnService(request.getDateMiseEnService())
                .status(request.getStatus())
                .cylindree(request.getCylindree() == null ? 0 : request.getCylindree())
                .typeMoto(request.getTypeMoto() == null ? null : MotoType.fromValue(request.getTypeMoto()))
                .casqueInclus(Boolean.TRUE.equals(request.getCasqueInclus()))
                .build();
    }

    public static void updateEntity(Vehicle vehicle, VehicleRequest request, Agency agency) {
        vehicle.setAgency(agency);
        vehicle.setMarque(request.getMarque());
        vehicle.setModele(request.getModele());
        vehicle.setMatricule(request.getMatricule());
        vehicle.setPrixParJour(request.getPrixParJour());
        vehicle.setDateMiseEnService(request.getDateMiseEnService());
        vehicle.setStatus(request.getStatus());

        if (vehicle instanceof Car car) {
            car.setNombrePortes(request.getNombrePortes() == null ? 0 : request.getNombrePortes());
            car.setTypeCarburant(request.getTypeCarburant() == null ? null : FuelType.fromValue(request.getTypeCarburant()));
            car.setBoiteVitesse(request.getBoiteVitesse() == null ? null : GearBoxType.fromValue(request.getBoiteVitesse()));
        }
        if (vehicle instanceof Moto moto) {
            moto.setCylindree(request.getCylindree() == null ? 0 : request.getCylindree());
            moto.setTypeMoto(request.getTypeMoto() == null ? null : MotoType.fromValue(request.getTypeMoto()));
            moto.setCasqueInclus(Boolean.TRUE.equals(request.getCasqueInclus()));
        }
    }
}