package com.ys.citronix.farmManagement.domain.service.impl;

import com.ys.citronix.farmManagement.application.dto.request.TreeRequestDto;
import com.ys.citronix.farmManagement.application.dto.request.TreeUpdateRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.farmManagement.application.mapper.FieldMapper;
import com.ys.citronix.farmManagement.application.mapper.TreeMapper;
import com.ys.citronix.farmManagement.application.service.TreeApplicationService;
import com.ys.citronix.farmManagement.domain.exception.TreeCreationException;
import com.ys.citronix.farmManagement.domain.model.Field;
import com.ys.citronix.farmManagement.domain.model.Tree;
import com.ys.citronix.farmManagement.domain.service.TreeService;
import com.ys.citronix.farmManagement.infrastructure.repository.TreeRepository;
import com.ys.citronix.sharedkernel.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.NotActiveException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreeDomainService implements TreeService , TreeApplicationService {
    private final TreeRepository repository;
    private final TreeMapper mapper;
    private final FieldMapper fieldMapper;

    @Override
    public  List<TreeResponseDto> getAllTreesByField(FieldResponseDto field){
        List<Tree> trees = repository.findAllByField(fieldMapper.toEntity(field));
        return trees.stream().map(mapper::toDto).toList();
    }


    @Override
    public List<TreeResponseDto> createTrees(FieldResponseDto field, List<TreeRequestDto> treesRequestDto) {


        treesRequestDto.stream()
                .filter(t -> t.plantingDate().getMonthValue() < 3 || t.plantingDate().getMonthValue() > 5)
                .findFirst()
                .ifPresent(t -> {
                    throw new TreeCreationException("Trees can only be planted between March and May.");
                });

        Integer TreesCount = repository.countByField(fieldMapper.toEntity(field));

        if (TreesCount + treesRequestDto.size() > (int) (field.area() / 1000) * 10) {
            throw new TreeCreationException("Field cannot exceed 10 trees per hectare (10 trees per 1000 m²).");
        }


        List<Tree> trees = treesRequestDto.stream()
                .map(t -> {
                    Tree tree = mapper.toEntity(t);
                    tree.setField(fieldMapper.toEntity(field));
                    return tree;
                })
                .collect(Collectors.toList());

        List<Tree> savedTrees = repository.saveAll(trees);

        return savedTrees.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public TreeResponseDto updateTree(TreeUpdateRequestDto treeRequestDto) {
        Tree existingTree = repository.findById(treeRequestDto.id())
                .orElseThrow(() -> new NotFoundException("Tree", treeRequestDto.id()));

        if (treeRequestDto.plantingDate().getMonthValue() < 3 || treeRequestDto.plantingDate().getMonthValue() > 5) {
            throw new TreeCreationException("Trees can only be planted between March and May.");
        }

        Field field = existingTree.getField();
        int treesCountInField = repository.countByField(field);

        if (treesCountInField >  (int) (field.getArea() / 1000) * 10) {
            throw new TreeCreationException("Field cannot exceed 10 trees per hectare (10 trees per 1000 m²).");
        }

        Tree updatedTree = mapper.toEntity(treeRequestDto);
        updatedTree.setField(field);
        return mapper.toDto(repository.save(updatedTree));
    }

    @Override
    public List<TreeResponseDto> findAllTreesByIds(List<UUID> ids) {
        List<Tree> trees = repository.findAllById(ids);
        return trees.stream().map(mapper::toDto).toList();
    }

    @Override
    public void deleteTree(UUID id) {
        if (!repository.existsById(id)){
            throw new NotFoundException("Tree", id);
        }
        repository.deleteById(id);
    }
}
