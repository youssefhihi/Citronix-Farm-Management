package com.ys.citronix.farmManagement.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record FarmRequestDto(
        @NotBlank(message = "farm name must not be blank")
        String name,
        @NotBlank(message = "farm location must not be blank")
        String location,
        @Positive(message = "farm area must be greater than zero")
        Double area,
        @NotNull(message = "date create of farm is required")
        LocalDateTime createdDateTime
) {
    public FarmRequestDto{
        if(createdDateTime.isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("date create of farm must not be in future.");
        }
    }
}
