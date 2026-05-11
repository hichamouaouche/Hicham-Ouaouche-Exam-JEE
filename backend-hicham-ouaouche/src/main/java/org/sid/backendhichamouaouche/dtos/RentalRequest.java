package org.sid.backendhichamouaouche.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequest {

    @NotNull
    @NotBlank
    private String vehicleId;

    @NotBlank
    private String customerName;

    @NotBlank
    private String customerPhone;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @AssertTrue(message = "endDate must be after startDate")
    public boolean isDateRangeValid() {
        if (startDate == null || endDate == null) {
            return true;
        }
        return endDate.isAfter(startDate);
    }
}