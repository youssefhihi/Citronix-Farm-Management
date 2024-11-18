package com.ys.citronix.farmManagement.application.mapper;

import com.ys.citronix.farmManagement.application.dto.request.TreeRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.farmManagement.domain.model.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    @Mapping(target = "createdDate" ,expression = "java(java.time.LocalDateTime.now())")
    Tree toEntity(TreeRequestDto treeRequestDto);
    @Mapping(target = "field", expression = "java(tree.getField().getName())")
    @Mapping(target = "age", expression = "java(tree.getAge())")
    @Mapping(target = "ProductivityPerYear",expression = "java(tree.getAnnualProductivity())")
    TreeResponseDto toDto(Tree tree);
    @Mapping(target = "field", ignore = true)
    Tree toEntity(TreeResponseDto treeResponseDto);
}
