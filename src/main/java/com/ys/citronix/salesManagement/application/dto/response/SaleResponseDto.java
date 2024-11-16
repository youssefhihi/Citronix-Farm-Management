package com.ys.citronix.salesManagement.application.dto.response;

import com.ys.citronix.harvestManagement.domain.model.Harvest;
import com.ys.citronix.salesManagement.domain.valueObject.SaleId;

import java.time.LocalDateTime;

public record SaleResponseDto(
        SaleId id,
        LocalDateTime saleDate,
        String clientName,
        Double quantitySold,
        Double pricePerUnit,
        Harvest harvest
) {
}
