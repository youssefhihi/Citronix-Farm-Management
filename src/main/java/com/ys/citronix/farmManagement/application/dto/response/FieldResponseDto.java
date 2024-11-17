package com.ys.citronix.farmManagement.application.dto.response;

import com.ys.citronix.farmManagement.domain.model.Farm;
import com.ys.citronix.farmManagement.domain.model.Tree;

import java.util.List;
import java.util.UUID;

public record FieldResponseDto(
        UUID id,
        String name,
        Double area,
        String farm
) {
}
