package com.ys.citronix.harvestManagement.application.mapper;

import com.ys.citronix.harvestManagement.application.dto.request.HarvestRequestDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;
import com.ys.citronix.harvestManagement.domain.model.Harvest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HarvestMapper {
    Harvest toEntity(HarvestRequestDto harvestRequestDto);
    HarvestResponseDto toDto(Harvest harvest);
}
