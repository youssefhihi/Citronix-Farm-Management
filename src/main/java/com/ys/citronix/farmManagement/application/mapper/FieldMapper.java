package com.ys.citronix.farmManagement.application.mapper;

import com.ys.citronix.farmManagement.application.dto.request.FieldRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.domain.model.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="Spring")
public interface FieldMapper {
    Field toEntity(FieldRequestDto fieldRequestDto);
    @Mapping(target = "farm", expression = "java(field.getFarm().getName())")
    FieldResponseDto toDto(Field field);
}
