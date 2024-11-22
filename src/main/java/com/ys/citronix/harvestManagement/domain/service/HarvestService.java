package com.ys.citronix.harvestManagement.domain.service;

import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.domain.model.Field;
import com.ys.citronix.harvestManagement.application.dto.request.HarvestRequestDto;
import com.ys.citronix.harvestManagement.application.dto.request.HarvestUpdateRequestDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;

import java.util.UUID;

public interface HarvestService {
    HarvestResponseDto createHarvest(HarvestRequestDto harvestRequestDto);
    void deleteHarvest(UUID id);
    HarvestResponseDto updateHarvest(HarvestUpdateRequestDto harvestRequestDto);
}
