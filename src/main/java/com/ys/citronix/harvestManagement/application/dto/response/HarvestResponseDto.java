package com.ys.citronix.harvestManagement.application.dto.response;

import com.ys.citronix.harvestManagement.domain.enums.Season;
import com.ys.citronix.harvestManagement.domain.valueObject.HarvestId;

import java.time.LocalDateTime;

public record HarvestResponseDto(
        HarvestId id,
        LocalDateTime harvestDate,
        Double  totalQuantity,
        Season season
) {
}
