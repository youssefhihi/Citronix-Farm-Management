package com.ys.citronix.farmManagement.application.mapper;

import com.ys.citronix.farmManagement.application.dto.request.TreeRequestDto;
import com.ys.citronix.farmManagement.application.dto.request.TreeUpdateRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.farmManagement.domain.model.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = FieldMapper.class)
public interface TreeMapper {
    @Mapping(target = "createdDate" ,expression = "java(java.time.LocalDateTime.now())")
    Tree toEntity(TreeRequestDto treeRequestDto);
    @Mapping(target = "age", expression = "java(tree.getAge())")
    @Mapping(target = "ProductivityPerYear",expression = "java(tree.getAnnualProductivity())")
    @Mapping(target = "field", source = "field")
    TreeResponseDto toDto(Tree tree);
    @Mapping(target = "field", source = "field")
    Tree toEntity(TreeResponseDto treeResponseDto);

    Tree toEntity(TreeUpdateRequestDto treeUpdateRequestDto);
}
