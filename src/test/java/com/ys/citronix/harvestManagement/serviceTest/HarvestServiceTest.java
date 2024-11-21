package com.ys.citronix.harvestManagement.serviceTest;

import com.ys.citronix.farmManagement.application.dto.response.FarmResponseDto;
import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.farmManagement.application.mapper.FarmMapper;
import com.ys.citronix.farmManagement.application.mapper.FieldMapper;
import com.ys.citronix.farmManagement.application.service.TreeApplicationService;
import com.ys.citronix.farmManagement.domain.model.Farm;
import com.ys.citronix.harvestManagement.application.dto.request.HarvestRequestDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;
import com.ys.citronix.harvestManagement.application.mapper.HarvestMapper;
import com.ys.citronix.harvestManagement.application.service.HarvestDetailsApplicationService;
import com.ys.citronix.harvestManagement.domain.enums.Season;
import com.ys.citronix.harvestManagement.domain.events.HarvestCreatedEvent;
import com.ys.citronix.harvestManagement.domain.exception.HarvestCreationException;
import com.ys.citronix.harvestManagement.domain.model.Harvest;
import com.ys.citronix.harvestManagement.domain.service.impl.HarvestDomainService;
import com.ys.citronix.harvestManagement.infrastructure.repository.HarvestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HarvestServiceTest {

    @Mock
    private HarvestRepository repository;

    @Mock
    private HarvestMapper mapper;

    @Mock
    private HarvestDetailsApplicationService harvestDetailsService;

    @Mock
    private TreeApplicationService treeService;

    @Mock
    private FieldMapper fieldMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private FarmMapper farmMapper;

    @InjectMocks
    private HarvestDomainService service;

    private HarvestRequestDto requestDto;
    private FarmResponseDto farmResponseDto;
    private FieldResponseDto fieldResponseDto;
    private TreeResponseDto tree1;
    private TreeResponseDto tree2;

    @BeforeEach
    void setUp() {
        requestDto = mock(HarvestRequestDto.class);
        when(requestDto.treeId()).thenReturn(List.of(UUID.randomUUID(), UUID.randomUUID()));

        farmResponseDto = new FarmResponseDto(UUID.randomUUID(), "Farm1","location",6000.0, LocalDateTime.now());
        fieldResponseDto = new FieldResponseDto(UUID.randomUUID(), "Field1", 3000.0, farmResponseDto);

        tree1 = mock(TreeResponseDto.class);
        tree2 = mock(TreeResponseDto.class);
    }


    @Test
    void createHarvest_throwsExceptionForTreeNotFound() {

        when(treeService.findAllTreesByIds(requestDto.treeId())).thenReturn(List.of());

        assertThrows(HarvestCreationException.class, () -> service.createHarvest(requestDto));
        verify(treeService, times(1)).findAllTreesByIds(requestDto.treeId());
        verifyNoInteractions(repository, eventPublisher);
    }

    @Test
    void createHarvest_throwsExceptionForMismatchedFields() {

        FieldResponseDto differentFieldResponseDto = new FieldResponseDto(UUID.randomUUID(), "Field2", 2000.0, farmResponseDto);
        when(tree1.field()).thenReturn(fieldResponseDto);
        when(tree2.field()).thenReturn(differentFieldResponseDto);

        when(treeService.findAllTreesByIds(requestDto.treeId())).thenReturn(List.of(tree1, tree2));

        assertThrows(HarvestCreationException.class, () -> service.createHarvest(requestDto));
        verify(treeService, times(1)).findAllTreesByIds(requestDto.treeId());
        verifyNoInteractions(repository, eventPublisher);
    }
}
