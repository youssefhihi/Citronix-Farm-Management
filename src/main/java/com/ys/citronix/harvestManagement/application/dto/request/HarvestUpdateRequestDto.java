package com.ys.citronix.harvestManagement.application.dto.request;

import com.ys.citronix.harvestManagement.domain.enums.Season;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record HarvestUpdateRequestDto(
        @NotNull
        UUID id,
        @NotNull(message = "harvest date must not be null")
        LocalDateTime harvestDate,
        @NotNull(message = "season must not be null")
        Season season,
        @NotNull
        List<UUID> treeId
) {
}
