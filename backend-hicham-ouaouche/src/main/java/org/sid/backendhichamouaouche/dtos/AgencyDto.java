package org.sid.backendhichamouaouche.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgencyDto {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String phone;
    private long vehicleCount;
}