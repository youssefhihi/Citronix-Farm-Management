package com.ys.citronix.harvestManagement.domain.service;

import com.ys.citronix.harvestManagement.application.dto.response.HarvestDetailsResponseDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;

import java.util.List;
import java.util.UUID;

public interface HarvestDetailsService {
    List<HarvestDetailsResponseDto> getHarvestDetailsByHarvest(HarvestResponseDto harvest);
    void deleteHarvestDetailsById(UUID id);
}
