package com.ys.citronix.farmManagement.domain.service;


import com.ys.citronix.farmManagement.application.dto.request.FarmRequestDto;
import com.ys.citronix.farmManagement.application.dto.request.FarmRequestUpdateDto;
import com.ys.citronix.farmManagement.application.dto.response.FarmResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface FarmService {
    Page<FarmResponseDto> getAllFarms(Pageable pageable);
    FarmResponseDto createFarm(FarmRequestDto farmRequestDto);
    FarmResponseDto updateFarm(FarmRequestUpdateDto farmRequestDto);
    void deleteFarm(UUID farmId);
    List<FarmResponseDto>  findFarmMultiCriteriaSearch(String query);
}
