package com.ys.citronix.farmManagement.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record FarmResponseDto (
        UUID id,
        String name,
        String location,
        Double area,
        LocalDateTime createdDateTime
){
}
