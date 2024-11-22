package com.ys.citronix.farmManagement.application.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record TreeUpdateRequestDto(
        @NotNull
        UUID id,
        @NotNull(message = "planting date of tree is required")
        LocalDateTime plantingDate
) {
    public TreeUpdateRequestDto{
        if(plantingDate.isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("planting date of tree must not be in future.");
        }
    }
}
