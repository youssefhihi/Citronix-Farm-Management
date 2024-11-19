package com.ys.citronix.harvestManagement.application.dto.response;

import com.ys.citronix.harvestManagement.domain.enums.Season;

import java.time.LocalDateTime;
import java.util.UUID;

public record HarvestResponseDto(
        UUID id,
        LocalDateTime harvestDate,
        Double  totalQuantity,
        Season season
) {
}
