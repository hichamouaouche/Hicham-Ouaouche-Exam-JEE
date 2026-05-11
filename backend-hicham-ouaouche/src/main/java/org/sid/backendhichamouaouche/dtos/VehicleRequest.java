package org.sid.backendhichamouaouche.dtos;

import org.sid.backendhichamouaouche.enums.VehicleStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequest {

    @NotNull
    private Long agencyId;

    @NotBlank
    private String vehicleType;

    @NotBlank
    private String marque;

    @NotBlank
    private String modele;

    @NotBlank
    private String matricule;

    @Positive
    private double prixParJour;

    @NotNull
    private LocalDate dateMiseEnService;

    @NotNull
    private VehicleStatus status;

    private Integer nombrePortes;
    private String typeCarburant;
    private String boiteVitesse;
    private Integer cylindree;
    private String typeMoto;
    private Boolean casqueInclus;
}