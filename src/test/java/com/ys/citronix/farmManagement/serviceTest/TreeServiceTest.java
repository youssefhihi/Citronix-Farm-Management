package com.ys.citronix.farmManagement.serviceTest;

import com.ys.citronix.farmManagement.application.dto.request.TreeRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.FarmResponseDto;
import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.farmManagement.application.mapper.FarmMapper;
import com.ys.citronix.farmManagement.application.mapper.FieldMapper;
import com.ys.citronix.farmManagement.application.mapper.TreeMapper;
import com.ys.citronix.farmManagement.domain.exception.TreeCreationException;
import com.ys.citronix.farmManagement.domain.model.Farm;
import com.ys.citronix.farmManagement.domain.model.Field;
import com.ys.citronix.farmManagement.domain.model.Tree;
import com.ys.citronix.farmManagement.domain.service.impl.TreeDomainService;
import com.ys.citronix.farmManagement.infrastructure.repository.TreeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TreeServiceTest {

    @Mock
    private TreeRepository repository;

    @Mock
    private TreeMapper mapper;

    @Mock
    private FieldMapper fieldMapper;

    @Mock
    private FarmMapper farmMapper;

    @InjectMocks
    private TreeDomainService service;

    private FieldResponseDto fieldDto;
    private Field fieldEntity;
    private List<Tree> trees;
    private List<TreeResponseDto> expectedResponse;

    @BeforeEach
    public void setUp() {
        FarmResponseDto farmResponseDto = new FarmResponseDto(UUID.randomUUID(), "name", "location", 8000.0, LocalDateTime.now());
        fieldDto = new FieldResponseDto(UUID.randomUUID(), "Field 1", 2000.0, farmResponseDto);
        fieldEntity = new Field();
        fieldEntity.setId(fieldDto.id());
        fieldEntity.setName(fieldDto.name());
        fieldEntity.setArea(fieldDto.area());
        fieldEntity.setFarm(farmMapper.toEntity(farmResponseDto));

        Tree tree1 = new Tree();
        tree1.setId(UUID.randomUUID());
        tree1.setPlantingDate(LocalDateTime.of(2023, 3, 15, 0, 0));
        tree1.setField(fieldEntity);

        Tree tree2 = new Tree();
        tree2.setId(UUID.randomUUID());
        tree2.setPlantingDate(LocalDateTime.of(2023, 4, 10, 0, 0));
        tree2.setField(fieldEntity);

        trees = List.of(tree1, tree2);

        expectedResponse = List.of(
                new TreeResponseDto(trees.get(0).getId(), trees.get(0).getPlantingDate(), trees.get(0).getAge(), trees.get(0).getAnnualProductivity(), fieldDto),
                new TreeResponseDto(trees.get(1).getId(), trees.get(1).getPlantingDate(), trees.get(1).getAge(), trees.get(1).getAnnualProductivity(), fieldDto)
        );
    }

    @Test
    void getAllTreesByField_success() {
        when(fieldMapper.toEntity(fieldDto)).thenReturn(fieldEntity);
        when(repository.findAllByField(fieldEntity)).thenReturn(trees);
        when(mapper.toDto(trees.get(0))).thenReturn(expectedResponse.get(0));
        when(mapper.toDto(trees.get(1))).thenReturn(expectedResponse.get(1));

        List<TreeResponseDto> actualResponse = service.getAllTreesByField(fieldDto);

        assertEquals(expectedResponse, actualResponse);
        verify(fieldMapper, times(1)).toEntity(fieldDto);
        verify(repository, times(1)).findAllByField(fieldEntity);
        verify(mapper, times(trees.size())).toDto(any(Tree.class));
    }

    @Test
    void createTrees_success() {
        List<TreeRequestDto> treeRequests = List.of(
                new TreeRequestDto(LocalDateTime.of(2023, 3, 15, 0, 0)),
                new TreeRequestDto(LocalDateTime.of(2023, 4, 10, 0, 0))
        );

        when(fieldMapper.toEntity(fieldDto)).thenReturn(fieldEntity);
        when(repository.countByField(fieldEntity)).thenReturn(0);
        when(mapper.toEntity(any(TreeRequestDto.class)))
                .thenAnswer(invocation -> {
                    TreeRequestDto requestDto = invocation.getArgument(0);
                    Tree tree = new Tree();
                    tree.setId(UUID.randomUUID());
                    tree.setPlantingDate(requestDto.plantingDate());
                    tree.setField(fieldEntity);
                    return tree;
                });
        when(repository.saveAll(anyList())).thenReturn(trees);
        when(mapper.toDto(trees.get(0))).thenReturn(expectedResponse.get(0));
        when(mapper.toDto(trees.get(1))).thenReturn(expectedResponse.get(1));

        List<TreeResponseDto> actualResponse = service.createTrees(fieldDto, treeRequests);

        assertEquals(expectedResponse, actualResponse);
        verify(fieldMapper, times(3)).toEntity(fieldDto);
        verify(repository, times(1)).countByField(fieldEntity);
        verify(repository, times(1)).saveAll(anyList());
        verify(mapper, times(trees.size())).toDto(any(Tree.class));
    }

    @Test
    void createTrees_failure_plantingOutsideAllowedPeriod() {
        List<TreeRequestDto> treeRequests = List.of(
                new TreeRequestDto(LocalDateTime.of(2023, 2, 15, 0, 0))
        );

        TreeCreationException exception = assertThrows(
                TreeCreationException.class,
                () -> service.createTrees(fieldDto, treeRequests)
        );

        assertEquals("Trees can only be planted between March and May.", exception.getMessage());
        verify(repository, never()).saveAll(anyList());
    }

    @Test
    void createTrees_failure_exceedingTreeDensity() {
        List<TreeRequestDto> treeRequests = List.of(
                new TreeRequestDto(LocalDateTime.of(2023, 3, 15, 0, 0))
        );

        when(fieldMapper.toEntity(fieldDto)).thenReturn(fieldEntity);
        when(repository.countByField(fieldEntity)).thenReturn(20);

        TreeCreationException exception = assertThrows(
                TreeCreationException.class,
                () -> service.createTrees(fieldDto, treeRequests)
        );

        assertEquals("Field cannot exceed 10 trees per hectare (10 trees per 1000 m²).", exception.getMessage());
        verify(repository, never()).saveAll(anyList());
    }

    @Test
    void findAllTreesByIds_success() {
        List<UUID> treeIds = List.of(UUID.randomUUID(), UUID.randomUUID());

        Tree tree1 = new Tree();
        tree1.setId(treeIds.get(0));
        tree1.setPlantingDate(LocalDateTime.of(2023, 3, 15, 0, 0));
        tree1.setField(fieldEntity);

        Tree tree2 = new Tree();
        tree2.setId(treeIds.get(1));
        tree2.setPlantingDate(LocalDateTime.of(2023, 4, 10, 0, 0));
        tree2.setField(fieldEntity);

        trees = List.of(tree1, tree2);

        List<TreeResponseDto> expectedResponse = trees.stream()
                .map(t -> new TreeResponseDto(t.getId(), t.getPlantingDate(), t.getAge(), t.getAnnualProductivity(), fieldDto))
                .collect(Collectors.toList());

        when(repository.findAllById(treeIds)).thenReturn(trees);
        when(mapper.toDto(trees.get(0))).thenReturn(expectedResponse.get(0));
        when(mapper.toDto(trees.get(1))).thenReturn(expectedResponse.get(1));

        List<TreeResponseDto> actualResponse = service.findAllTreesByIds(treeIds);

        assertEquals(expectedResponse, actualResponse);
        verify(repository, times(1)).findAllById(treeIds);
        verify(mapper, times(trees.size())).toDto(any(Tree.class));
    }
}
