package com.ys.citronix.harvestManagement.domain.service;

import com.ys.citronix.harvestManagement.application.dto.response.HarvestDetailsResponseDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;

import java.util.List;

public interface HarvestDetailsService {
    List<HarvestDetailsResponseDto> getHarvestDetailsByHarvest(HarvestResponseDto harvest);
}
