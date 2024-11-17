package com.ys.citronix.farmManagement.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record FieldRequestDto (
        @NotBlank(message = "field name must not be blank")
        String name,
        @Positive(message = "the farm area must be greater than zero")
        Double area
){
}
