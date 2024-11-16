package com.ys.citronix.farmManagement.application.mapper;

import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.domain.model.Field;
import org.mapstruct.Mapper;

@Mapper(componentModel="Spring")
public interface FieldMapper {
    Field toEntity(FieldResponseDto fieldRequestDto);
    FieldResponseDto toDto(Field field);
}
