package com.ys.citronix.farmManagement.application.service;

import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;

import java.util.UUID;

public interface FieldApplicationService {
    FieldResponseDto findFieldById(UUID id);
}
