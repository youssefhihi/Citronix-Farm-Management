package com.ys.citronix.harvestManagement.application.dto.response;

import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.farmManagement.domain.model.Tree;
import com.ys.citronix.harvestManagement.domain.model.Harvest;

import java.util.UUID;

public record HarvestDetailsResponseDto(
        UUID id,
        Double quantity,
        TreeResponseDto tree
) {
}
