package com.ys.citronix.farmManagement.application.mapper;

import com.ys.citronix.farmManagement.application.dto.request.FieldRequestDto;
import com.ys.citronix.farmManagement.application.dto.request.FieldUpdateRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.domain.model.Farm;
import com.ys.citronix.farmManagement.domain.model.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="Spring",  uses = FarmMapper.class)
public interface FieldMapper {
    @Mapping(target = "createdDate" ,expression = "java(java.time.LocalDateTime.now())")
    Field toEntity(FieldRequestDto fieldRequestDto);
    @Mapping(target = "farm", source = "farm")
    FieldResponseDto toDto(Field field);
    @Mapping(target = "farm", ignore = true)
    Field toEntity(FieldResponseDto fieldResponseDto);

    Field toEntity(FieldUpdateRequestDto fieldRequestDto);
}
