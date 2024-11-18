package com.ys.citronix.farmManagement.application.dto.response;


import java.time.LocalDateTime;
import java.util.UUID;

public record TreeResponseDto (
        UUID id,
        LocalDateTime plantingDate,
        Integer age,
        Double ProductivityPerYear,
        FieldResponseDto field
){
}
