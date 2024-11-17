package com.ys.citronix.farmManagement.domain.service.impl;

import com.ys.citronix.farmManagement.application.dto.request.TreeRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.farmManagement.application.mapper.FieldMapper;
import com.ys.citronix.farmManagement.application.mapper.TreeMapper;
import com.ys.citronix.farmManagement.domain.exception.TreeCreationException;
import com.ys.citronix.farmManagement.domain.model.Tree;
import com.ys.citronix.farmManagement.domain.service.TreeService;
import com.ys.citronix.farmManagement.infrastructure.repository.TreeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreeDomainService implements TreeService {
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
                    int age = (int) ChronoUnit.YEARS.between(t.plantingDate().toLocalDate(), LocalDate.now());
                    tree.setAge(age);
                    return tree;
                })
                .collect(Collectors.toList());

        List<Tree> savedTrees = repository.saveAll(trees);

        return savedTrees.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }


}
