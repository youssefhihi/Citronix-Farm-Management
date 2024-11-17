package com.ys.citronix.farmManagement.application.mapper;


import com.ys.citronix.farmManagement.application.dto.request.FarmRequestDto;
import com.ys.citronix.farmManagement.application.dto.request.FarmRequestUpdateDto;
import com.ys.citronix.farmManagement.application.dto.response.FarmResponseDto;
import com.ys.citronix.farmManagement.domain.model.Farm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FarmMapper {
    Farm toEntity(FarmRequestDto farmRequestDto);
    Farm toEntity(FarmRequestUpdateDto farmRequestUpdateDto);
    FarmResponseDto toDto(Farm farm);
}
