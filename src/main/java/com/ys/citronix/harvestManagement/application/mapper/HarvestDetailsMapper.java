package com.ys.citronix.harvestManagement.application.mapper;

import com.ys.citronix.harvestManagement.application.dto.request.HarvestDetailsRequestDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestDetailsResponseDto;
import com.ys.citronix.harvestManagement.domain.model.HarvestDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HarvestDetailsMapper {
    HarvestDetails toEntity(HarvestDetailsRequestDto dto);
    HarvestDetailsResponseDto toDto(HarvestDetails entity);
}
