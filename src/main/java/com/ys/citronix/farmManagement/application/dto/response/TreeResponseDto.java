package com.ys.citronix.farmManagement.application.dto.response;

import com.ys.citronix.farmManagement.domain.model.Field;
import com.ys.citronix.farmManagement.domain.valueObject.TreeId;

import java.time.LocalDateTime;
import java.util.UUID;

public record TreeResponseDto (
        UUID id,
        LocalDateTime plantingDate,
        Integer age,
        String field
){
}
