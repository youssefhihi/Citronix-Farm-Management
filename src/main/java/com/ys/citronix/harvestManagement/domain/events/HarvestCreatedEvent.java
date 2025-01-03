package com.ys.citronix.harvestManagement.domain.events;

import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;

import java.util.List;

public record HarvestCreatedEvent(HarvestResponseDto harvest, List<TreeResponseDto> trees) {
    public HarvestCreatedEvent{
        if (harvest == null || trees == null || trees.isEmpty()) {
            throw new IllegalArgumentException("Harvest and tree list must not be null or empty.");
        }
    }
}
