package com.ys.citronix.harvestManagement.serviceTest;

import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.farmManagement.application.mapper.TreeMapper;
import com.ys.citronix.farmManagement.domain.model.Farm;
import com.ys.citronix.harvestManagement.application.dto.request.HarvestDetailsRequestDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestDetailsResponseDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;
import com.ys.citronix.harvestManagement.application.mapper.HarvestDetailsMapper;
import com.ys.citronix.harvestManagement.application.mapper.HarvestMapper;
import com.ys.citronix.harvestManagement.domain.enums.Season;
import com.ys.citronix.harvestManagement.domain.model.Harvest;
import com.ys.citronix.harvestManagement.domain.model.HarvestDetails;
import com.ys.citronix.harvestManagement.domain.service.impl.HarvestDetailsDomainService;
import com.ys.citronix.harvestManagement.infrastructure.repository.HarvestDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HarvestDetailsTest {

    @Mock
    private HarvestDetailsRepository repository;

    @Mock
    private HarvestDetailsMapper mapper;

    @Mock
    private TreeMapper treeMapper;

    @Mock
    private HarvestMapper harvestMapper;

    @InjectMocks
    private HarvestDetailsDomainService service;

    private HarvestResponseDto harvestResponseDto;
    private TreeResponseDto treeResponseDto;
    private HarvestDetails harvestDetails;

    @BeforeEach
    void setUp() {
        harvestResponseDto = new HarvestResponseDto(
                UUID.randomUUID(),
                LocalDateTime.now(),
                34.5,
                Season.Spring

        );

        treeResponseDto = new TreeResponseDto(
                UUID.randomUUID(),
                LocalDateTime.now().minusYears(2),
                2,
                100.0,
                null
        );

        harvestDetails = new HarvestDetails();
        harvestDetails.setId(UUID.randomUUID());
        harvestDetails.setCreatedDate(LocalDateTime.now());
        harvestDetails.setQuantity(100.0);
    }

    @Test
    void createHarvestDetails_success() {
        HarvestDetailsRequestDto requestDto = new HarvestDetailsRequestDto(
                harvestResponseDto,
                List.of(treeResponseDto)
        );

        when(harvestMapper.toEntity(harvestResponseDto)).thenReturn(harvestDetails.getHarvest());
        when(treeMapper.toEntity(treeResponseDto)).thenReturn(harvestDetails.getTree());
        when(repository.saveAll(anyList())).thenReturn(List.of(harvestDetails));
        when(mapper.toDto(harvestDetails)).thenReturn(new HarvestDetailsResponseDto(
                harvestDetails.getId(),
                harvestDetails.getQuantity(),
                treeResponseDto
        ));


        service.createHarvestDetails(requestDto);

        verify(repository, times(1)).saveAll(anyList());
        verify(harvestMapper, times(1)).toEntity(harvestResponseDto);
        verify(treeMapper, times(1)).toEntity(treeResponseDto);
    }

    @Test
    void existsTreeInSeason_success() {
        UUID treeId = UUID.randomUUID();
        when(repository.existsByTree_IdAndHarvest_Season(treeId, Season.Spring)).thenReturn(true);


        boolean exists = service.existsTreeInSeason(treeId, Season.Spring);

        assertTrue(exists);
        verify(repository, times(1)).existsByTree_IdAndHarvest_Season(treeId, Season.Spring);
    }

    @Test
    void existsByTreeFieldSeasonAndYear_success() {
        Farm farm = mock(Farm.class);

        Harvest harvest = mock(Harvest.class);
        when(harvest.getHarvestDate()).thenReturn(LocalDateTime.now());
        harvestDetails.setHarvest(harvest);

        when(repository.findAllByTree_Field_FarmAndHarvest_Season(farm, Season.Spring))
                .thenReturn(List.of(harvestDetails));


        boolean exists = service.existsByTreeFieldSeasonAndYear(farm, Season.Spring, LocalDateTime.now().getYear());

        assertTrue(exists);
        verify(repository, times(1)).findAllByTree_Field_FarmAndHarvest_Season(farm, Season.Spring);
    }


    @Test
    void getHarvestDetailsByHarvest_success() {
        when(harvestMapper.toEntity(harvestResponseDto)).thenReturn(harvestDetails.getHarvest());
        when(repository.findAllByHarvest(harvestDetails.getHarvest())).thenReturn(List.of(harvestDetails));
        when(mapper.toDto(harvestDetails)).thenReturn(new HarvestDetailsResponseDto(
                harvestDetails.getId(),
                harvestDetails.getQuantity(),
                treeResponseDto
        ));


        List<HarvestDetailsResponseDto> response = service.getHarvestDetailsByHarvest(harvestResponseDto);

        assertEquals(1, response.size());
        verify(repository, times(1)).findAllByHarvest(harvestDetails.getHarvest());
        verify(mapper, times(1)).toDto(harvestDetails);
    }
}