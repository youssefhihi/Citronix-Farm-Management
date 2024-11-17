package com.ys.citronix.farmManagement.domain.service;

import com.ys.citronix.farmManagement.application.dto.request.FieldRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.FarmResponseDto;
import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.domain.model.Farm;

import java.util.List;
import java.util.UUID;

public interface FieldService {
    List<FieldResponseDto> addFields(FarmResponseDto farm, List<FieldRequestDto> fieldsRequestDto);
    List<FieldResponseDto> getAllFieldsByFarmId(FarmResponseDto farm);
}
