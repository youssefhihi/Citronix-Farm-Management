package com.ys.citronix.farmManagement.serviceTest;

import com.ys.citronix.farmManagement.application.dto.request.FarmRequestDto;
import com.ys.citronix.farmManagement.application.dto.request.FarmRequestUpdateDto;
import com.ys.citronix.farmManagement.application.dto.response.FarmResponseDto;
import com.ys.citronix.farmManagement.application.mapper.FarmMapper;
import com.ys.citronix.farmManagement.domain.model.Farm;
import com.ys.citronix.farmManagement.domain.service.impl.FarmDomainService;
import com.ys.citronix.farmManagement.infrastructure.repository.FarmRepository;
import com.ys.citronix.farmManagement.infrastructure.repository.FarmSearchRepository;
import com.ys.citronix.sharedkernel.domain.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FarmServiceTest {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FarmSearchRepository farmSearchRepository;

    @Mock
    private FarmMapper farmMapper;

    @InjectMocks
    private FarmDomainService farmDomainService;

    private Farm farm;
    private FarmResponseDto farmResponseDto;
    private FarmRequestDto farmRequestDto;
    private UUID farmId;

    @BeforeEach
    void setUp() {
        farmId = UUID.randomUUID();
        farm = new Farm();
        farmResponseDto = new FarmResponseDto(farmId, "Test Farm", "Test Location", 15.0, LocalDateTime.now());
        farmRequestDto = new FarmRequestDto("Test Farm", "Test Location", 15.0, LocalDateTime.now());
    }

    @Test
    void getAllFarms_Success() {

        List<Farm> farms = List.of(farm, farm);
        List<FarmResponseDto> expectedDtos = List.of(farmResponseDto, farmResponseDto);

        Mockito.when(farmRepository.findAll()).thenReturn(farms);
        Mockito.when(farmMapper.toDto(farm)).thenReturn(farmResponseDto);


        List<FarmResponseDto> result = farmDomainService.getAllFarms();


        assertEquals(expectedDtos, result);
        verify(farmRepository, times(1)).findAll();
        verify(farmMapper, times(2)).toDto(farm);
    }


    @Test
    void createFarm_Success() {

        Mockito.when(farmMapper.toEntity(farmRequestDto)).thenReturn(farm);
        Mockito.when(farmRepository.save(farm)).thenReturn(farm);
        Mockito.when(farmMapper.toDto(farm)).thenReturn(farmResponseDto);


        FarmResponseDto result = farmDomainService.createFarm(farmRequestDto);


        assertEquals(farmResponseDto, result);
        verify(farmMapper,times(1)).toEntity(farmRequestDto);
        verify(farmRepository,times(1)).save(farm);
        verify(farmMapper,times(1)).toDto(farm);
    }

    @Test
    void updateFarm_Failure() {

        FarmRequestUpdateDto updateDto = new FarmRequestUpdateDto(farmId, "Updated Farm", "Updated Location", 20.0, LocalDateTime.now());
        Mockito.when(farmRepository.existsById(updateDto.id())).thenReturn(false);

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> farmDomainService.updateFarm(updateDto)
        );

        assertEquals("Farm with id "+ updateDto.id()+ " not found", exception.getMessage());
        verify(farmRepository,times(1)).existsById(updateDto.id());
    }


    @Test
    void updateFarm_Success() {

        FarmRequestUpdateDto updateDto = new FarmRequestUpdateDto(farmId, "Updated Farm", "Updated Location", 20.0, LocalDateTime.now());
        FarmResponseDto updatedResponseDto = new FarmResponseDto(farmId, "Updated Farm", "Updated Location", 20.0,LocalDateTime.now());

        when(farmRepository.existsById(farmId)).thenReturn(true);
        when(farmMapper.toEntity(updateDto)).thenReturn(farm);
        when(farmRepository.save(farm)).thenReturn(farm);
        when(farmMapper.toDto(farm)).thenReturn(updatedResponseDto);


        FarmResponseDto result = farmDomainService.updateFarm(updateDto);


        assertEquals(updatedResponseDto, result);
        verify(farmRepository).existsById(farmId);
        verify(farmMapper).toEntity(updateDto);
        verify(farmRepository).save(farm);
        verify(farmMapper).toDto(farm);
    }

    @Test
    void deleteFarm_success() {

        when(farmRepository.existsById(farmId)).thenReturn(true);


        farmDomainService.deleteFarm(farmId);


        verify(farmRepository).existsById(farmId);
        verify(farmRepository).deleteById(farmId);
    }

     @Test
     void deleteFarm_Failure() {

        when(farmRepository.existsById(farmId)).thenReturn(false);

        NotFoundException exception = assertThrows(
                NotFoundException.class
                , () -> farmDomainService.deleteFarm(farmId)
        );

        assertEquals("Farm with id "+ farmId+ " not found", exception.getMessage());
        verify(farmRepository,times(1)).existsById(farmId);
     }

    @Test
    void findFarmById_success() {

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));
        when(farmMapper.toDto(farm)).thenReturn(farmResponseDto);


        FarmResponseDto result = farmDomainService.findFarmById(farmId);


        assertEquals(farmResponseDto, result);
        verify(farmRepository).findById(farmId);
        verify(farmMapper).toDto(farm);
    }

    @Test
    void findFarmById_NotFound() {
        when(farmRepository.findById(farmId)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(
                NotFoundException.class
                , () -> farmDomainService.findFarmById(farmId)
        );
        assertEquals("Farm with id "+ farmId+ " not found", exception.getMessage());
        verify(farmRepository,times(1)).findById(farmId);
    }

    @Test
    void searchFarm_success() {
        when(farmSearchRepository.findFarmMultiCriteriaSearch(farmRequestDto.name())).thenReturn(List.of(farm));
        when(farmMapper.toDto(farm)).thenReturn(farmResponseDto);
        List<FarmResponseDto> expectedDtos = List.of(farmResponseDto);
        List<FarmResponseDto> farms = farmDomainService.findFarmMultiCriteriaSearch(farmRequestDto.name());
        assertEquals(expectedDtos, farms);
        verify(farmSearchRepository,times(1)).findFarmMultiCriteriaSearch(farmRequestDto.name());
        verify(farmMapper,times(1)).toDto(farm);

    }

}
