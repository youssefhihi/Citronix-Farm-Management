package com.ys.citronix.harvestManagement.application.service;

import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;

import java.util.UUID;

public interface HarvestApplicationService {
    HarvestResponseDto findHarvestById(UUID harvestId);
}
