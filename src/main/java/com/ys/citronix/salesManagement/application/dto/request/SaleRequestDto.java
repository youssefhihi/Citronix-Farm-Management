package com.ys.citronix.salesManagement.application.dto.request;

import com.ys.citronix.harvestManagement.domain.model.Harvest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

public record SaleRequestDto(
        @NotNull(message = "Sale date must not be null")
        LocalDateTime saleDate,
        @NotNull(message = "Client name must not be null")
        String clientName,
        @Positive(message = "Quantity sold must be greater than zero")
        Double quantitySold,
        @Positive(message = "Price per unit must be greater than zero")
        Double pricePerUnit,
        @NotNull(message = "harvest must not be null")
        UUID harvest
) {
}
