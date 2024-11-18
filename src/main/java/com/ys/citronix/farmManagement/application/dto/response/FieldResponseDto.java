package com.ys.citronix.farmManagement.application.dto.response;


import java.util.UUID;

public record FieldResponseDto(
        UUID id,
        String name,
        Double area,
        String farm
) {
}
