package com.ys.citronix.farmManagement.application.mapper;


import com.ys.citronix.farmManagement.application.dto.request.FarmRequestDto;
import com.ys.citronix.farmManagement.application.dto.request.FarmRequestUpdateDto;
import com.ys.citronix.farmManagement.application.dto.response.FarmResponseDto;
import com.ys.citronix.farmManagement.domain.model.Farm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface FarmMapper {
    @Mapping(target = "createdDate" ,expression = "java(java.time.LocalDateTime.now())")
    Farm toEntity(FarmRequestDto farmRequestDto);
    Farm toEntity(FarmRequestUpdateDto farmRequestUpdateDto);
    Farm toEntity(FarmResponseDto farmRequestDto);
    FarmResponseDto toDto(Farm farm);
}
