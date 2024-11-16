package com.ys.citronix.farmManagement.application.mapper;

import com.ys.citronix.farmManagement.application.dto.request.TreeRequestDto;
import com.ys.citronix.farmManagement.domain.model.Tree;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    Tree toEntity(TreeRequestDto treeRequestDto);
    TreeRequestDto toDto(Tree tree);
}
