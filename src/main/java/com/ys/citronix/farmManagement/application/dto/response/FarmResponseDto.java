package com.ys.citronix.farmManagement.application.dto.response;

import com.ys.citronix.farmManagement.domain.model.Field;
import com.ys.citronix.farmManagement.domain.valueObject.FarmId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record FarmResponseDto (
        UUID id,
        String name,
        String location,
        Double area,
        LocalDateTime createdDateTime,
        List<Field>fields
){
}
