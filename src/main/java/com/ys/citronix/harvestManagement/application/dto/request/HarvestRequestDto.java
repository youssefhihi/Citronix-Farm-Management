package com.ys.citronix.harvestManagement.application.dto.request;

import com.ys.citronix.harvestManagement.domain.enums.Season;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record HarvestRequestDto(
        @NotNull(message = "harvest date must not be null")
        LocalDateTime harvestDate,
        @Positive(message = "total quantity must be greater than zero")
        Double  totalQuantity,
        @NotNull(message = "season must not be null")
        Season season
) {
}
