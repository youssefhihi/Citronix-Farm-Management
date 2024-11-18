package com.ys.citronix.harvestManagement.application.mapper;

import com.ys.citronix.farmManagement.application.mapper.TreeMapper;
import com.ys.citronix.harvestManagement.application.dto.request.HarvestDetailsRequestDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestDetailsResponseDto;
import com.ys.citronix.harvestManagement.domain.model.HarvestDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TreeMapper.class)
public interface HarvestDetailsMapper {
    HarvestDetails toEntity(HarvestDetailsRequestDto dto);

    @Mapping(target = "tree", source = "tree")
    HarvestDetailsResponseDto toDto(HarvestDetails entity);
}
