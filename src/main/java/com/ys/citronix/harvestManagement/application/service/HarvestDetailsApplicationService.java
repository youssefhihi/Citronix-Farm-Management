package com.ys.citronix.harvestManagement.application.service;

import com.ys.citronix.farmManagement.domain.model.Farm;
import com.ys.citronix.harvestManagement.application.dto.request.HarvestDetailsRequestDto;
import com.ys.citronix.harvestManagement.domain.enums.Season;

import java.util.UUID;

public interface HarvestDetailsApplicationService {
    Boolean existsTreeInSeason(UUID treeId, Season season);
    Boolean existsByTreeFieldSeasonAndYear(Farm farm, Season season, Integer year);
    void createHarvestDetails(HarvestDetailsRequestDto harvestDetailsRequestDto);
}
