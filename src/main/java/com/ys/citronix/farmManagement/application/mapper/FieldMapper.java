package com.ys.citronix.farmManagement.application.mapper;

import com.ys.citronix.farmManagement.application.dto.request.FieldRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.domain.model.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="Spring")
public interface FieldMapper {
    @Mapping(target = "createdDate" ,expression = "java(java.time.LocalDateTime.now())")
    Field toEntity(FieldRequestDto fieldRequestDto);
    @Mapping(target = "farm", expression = "java(field.getFarm() != null ? field.getFarm().getName() : null)")
    FieldResponseDto toDto(Field field);
    @Mapping(target = "farm", ignore = true)
    Field toEntity(FieldResponseDto fieldResponseDto);

}
