package org.sid.backendhichamouaouche.dtos;

import org.sid.backendhichamouaouche.enums.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {

    private String id;
    private Long agencyId;
    private String agencyName;
    private String vehicleType;
    private String marque;
    private String modele;
    private String matricule;
    private double prixParJour;
    private LocalDate dateMiseEnService;
    private VehicleStatus status;
    private Integer nombrePortes;
    private String typeCarburant;
    private String boiteVitesse;
    private Integer cylindree;
    private String typeMoto;
    private Boolean casqueInclus;
    private long rentalCount;
}