package com.ys.citronix.harvestManagement.domain.service;

import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.domain.model.Field;
import com.ys.citronix.harvestManagement.application.dto.request.HarvestRequestDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;

public interface HarvestService {
    HarvestResponseDto createHarvest(FieldResponseDto field, HarvestRequestDto harvestRequestDto);
}
