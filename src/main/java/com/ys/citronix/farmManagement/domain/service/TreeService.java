package com.ys.citronix.farmManagement.domain.service;

import com.ys.citronix.farmManagement.application.dto.request.TreeRequestDto;
import com.ys.citronix.farmManagement.application.dto.request.TreeUpdateRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;

import java.util.List;
import java.util.UUID;

public interface TreeService {
    List<TreeResponseDto> createTrees(FieldResponseDto field, List<TreeRequestDto> treesRequestDto);
    List<TreeResponseDto> getAllTreesByField(FieldResponseDto field);
    TreeResponseDto updateTree(TreeUpdateRequestDto treeRequestDto);
    void deleteTree(UUID id);
}
