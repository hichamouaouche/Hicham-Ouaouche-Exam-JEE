package org.sid.backendhichamouaouche.entities;

import org.sid.backendhichamouaouche.enums.FuelType;
import org.sid.backendhichamouaouche.enums.GearBoxType;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("CAR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Car extends Vehicle {

    private int nombrePortes;

    private FuelType typeCarburant;

    private GearBoxType boiteVitesse;
}