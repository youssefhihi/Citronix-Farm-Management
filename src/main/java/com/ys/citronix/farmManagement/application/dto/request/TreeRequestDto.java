package com.ys.citronix.farmManagement.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

public record TreeRequestDto (
        @NotNull(message = "planting date of tree is required")
        LocalDateTime plantingDate,

        @Positive(message = "tree age must greater than zero")
        Integer age,

        @NotNull
        UUID fieldId

){
    public TreeRequestDto{
        if(plantingDate.isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("planting date of tree must not be in future.");
        }
    }
}
