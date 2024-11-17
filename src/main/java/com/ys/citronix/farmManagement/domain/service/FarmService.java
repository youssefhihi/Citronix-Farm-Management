package com.ys.citronix.farmManagement.domain.service;


import com.ys.citronix.farmManagement.application.dto.request.FarmRequestDto;
import com.ys.citronix.farmManagement.application.dto.request.FarmRequestUpdateDto;
import com.ys.citronix.farmManagement.application.dto.response.FarmResponseDto;
import com.ys.citronix.farmManagement.domain.valueObject.FarmId;

import java.util.List;
import java.util.UUID;

public interface FarmService {
    List<FarmResponseDto> getAllFarms();
    FarmResponseDto createFarm(FarmRequestDto farmRequestDto);
    FarmResponseDto updateFarm(FarmRequestUpdateDto farmRequestDto);
    Boolean deleteFarm(UUID farmId);
}
