package com.ys.citronix.harvestManagement.application.service;

import com.ys.citronix.farmManagement.domain.model.Field;
import com.ys.citronix.harvestManagement.application.dto.request.HarvestDetailsRequestDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestDetailsResponseDto;
import com.ys.citronix.harvestManagement.domain.enums.Season;

import java.util.List;
import java.util.UUID;

public interface HarvestDetailsApplicationService {
    Boolean existsTreeInSeason(UUID treeId, Season season);
    Boolean existsByTreeFieldSeasonAndYear(Field field, Season season, Integer year);
    List<HarvestDetailsResponseDto> createHarvestDetails(HarvestDetailsRequestDto harvestDetailsRequestDto);
}
