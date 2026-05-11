package org.sid.backendhichamouaouche.entities;

import org.sid.backendhichamouaouche.enums.MotoType;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("MOTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Moto extends Vehicle {

    private int cylindree;

    private MotoType typeMoto;

    private boolean casqueInclus;
}