package com.ys.citronix.salesManagement.application.dto.response;

import com.ys.citronix.harvestManagement.domain.model.Harvest;

import java.time.LocalDateTime;
import java.util.UUID;

public record SaleResponseDto(
        UUID id,
        LocalDateTime saleDate,
        String clientName,
        Double quantitySold,
        Double pricePerUnit,
        Harvest harvest
) {
}
