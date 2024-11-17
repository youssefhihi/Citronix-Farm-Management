package com.ys.citronix.farmManagement.application.mapper;

import com.ys.citronix.farmManagement.application.dto.request.TreeRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.farmManagement.domain.model.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    Tree toEntity(TreeRequestDto treeRequestDto);
    @Mapping(target = "field", expression = "java(tree.getField().getName())")
    TreeResponseDto toDto(Tree tree);
}
