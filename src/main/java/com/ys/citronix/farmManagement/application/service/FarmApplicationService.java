package com.ys.citronix.farmManagement.application.service;

import com.ys.citronix.farmManagement.application.dto.response.FarmResponseDto;

import java.util.UUID;

public interface FarmApplicationService {
    FarmResponseDto findFarmById(UUID id);
}
