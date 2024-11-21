package com.ys.citronix.farmManagement.serviceTest;

import com.ys.citronix.farmManagement.application.dto.request.FieldRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.FarmResponseDto;
import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.application.mapper.FarmMapper;
import com.ys.citronix.farmManagement.application.mapper.FieldMapper;
import com.ys.citronix.farmManagement.domain.exception.FieldCreationException;
import com.ys.citronix.farmManagement.domain.model.Farm;
import com.ys.citronix.farmManagement.domain.model.Field;
import com.ys.citronix.farmManagement.domain.service.impl.FieldDomainService;
import com.ys.citronix.farmManagement.infrastructure.repository.FieldRepository;
import com.ys.citronix.sharedkernel.domain.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
public class FieldServiceTest {

    @Mock
    private FieldRepository repository;

    @Mock
    private FieldMapper mapper;

    @Mock
    private FarmMapper farmMapper;

    @InjectMocks
    private FieldDomainService service;

    private Field field;
    private FieldRequestDto fieldRequestDto;
    private FieldResponseDto fieldResponseDto;
    private FarmResponseDto farmResponseDto;
    private Farm farm;
    private UUID fieldId;

    @BeforeEach
    public void setUp() {
        fieldId = UUID.randomUUID();
        farm = new Farm();
        farm.setArea(6100.0);
        farmResponseDto = new FarmResponseDto(fieldId, "Test Farm", "Test Location", 6100.0, LocalDateTime.now());
        field = new Field();
        fieldRequestDto = new FieldRequestDto("field A", 3000.0);
        fieldResponseDto = new FieldResponseDto(fieldId, "field A", 3000.0, farmResponseDto);

    }

    @Test
    void createField_success() {
        when(farmMapper.toEntity(farmResponseDto)).thenReturn(farm);
        when(mapper.toEntity(fieldRequestDto)).thenReturn(field);
        when(mapper.toDto(field)).thenReturn(fieldResponseDto);
        when(repository.saveAll(List.of(field, field))).thenReturn(List.of(field, field));


        List<FieldResponseDto> response = service.addFields(farmResponseDto, List.of(fieldRequestDto, fieldRequestDto));

        assertEquals(List.of(fieldResponseDto, fieldResponseDto), response);
        verify(repository, times(1)).saveAll(List.of(field, field));
        verify(mapper, times(2)).toEntity(fieldRequestDto);
        verify(mapper, times(2)).toDto(field);
        verify(farmMapper, times(3)).toEntity(farmResponseDto);
    }

    @Test
    void createField_failure_exceedsFarmArea() {
        FieldRequestDto largeField1 = new FieldRequestDto("Large Field 1", 4000.0);
        FieldRequestDto largeField2 = new FieldRequestDto("Large Field 2", 3000.0);
        Farm farmEntity = new Farm();
        field.setArea(3000.0);
        when(repository.findAllByFarm(farmEntity)).thenReturn(List.of(field));
        when(farmMapper.toEntity(farmResponseDto)).thenReturn(farmEntity);

        FieldCreationException exception = assertThrows(
                FieldCreationException.class,
                () -> service.addFields(farmResponseDto, List.of(largeField1, largeField2))
        );

        assertEquals(
                "Total area of fields cannot be greater than or equal to the farm area.",
                exception.getMessage()
        );

        verify(repository, times(1)).findAllByFarm(farmEntity);
        verify(mapper, never()).toEntity(any(FieldRequestDto.class));
        verify(repository, never()).saveAll(anyList());
    }


    @Test
    void createField_failure_exceedsMaxFields() {
        List<Field> existingFields = new ArrayList<>();
        for (int i = 0; i < 11 ; i++) {
            field.setArea(i * 10.0);
            existingFields.add(field);
        }

        when(repository.findAllByFarm(any(Farm.class))).thenReturn(existingFields);
        when(farmMapper.toEntity(farmResponseDto)).thenReturn(new Farm());

        FieldCreationException exception = assertThrows(
                FieldCreationException.class,
                () -> service.addFields(farmResponseDto, List.of(fieldRequestDto))
        );

        assertEquals("A farm cannot have more than 10 fields.", exception.getMessage());
        verify(repository, times(1)).findAllByFarm(any(Farm.class));
        verify(mapper, never()).toEntity(fieldRequestDto);
    }

    @Test
    void createField_failure_belowMinimumArea() {
        FarmResponseDto validFarm = new FarmResponseDto(fieldId, "Valid Farm", "Test Location", 10000.0, LocalDateTime.now());
        FieldRequestDto smallField = new FieldRequestDto("Field Y", 500.0);
        when(repository.findAllByFarm(any(Farm.class))).thenReturn(List.of());
        when(farmMapper.toEntity(validFarm)).thenReturn(new Farm());

        FieldCreationException exception = assertThrows(
                FieldCreationException.class,
                () -> service.addFields(validFarm, List.of(smallField))
        );

        assertEquals("Area of Field Y field must be at least 1000 m² (0.1 hectare).", exception.getMessage());
    }

    @Test
    void createField_failure_exceedsHalfFarmArea() {
        FieldRequestDto oversizedField = new FieldRequestDto("Oversized Field", 4000.0); // Exceeds 50% of 6000 m² farm
        farm.setArea(6000.0);
        field.setArea(2000.0);

        when(farmMapper.toEntity(farmResponseDto)).thenReturn(farm);
        when(repository.findAllByFarm(farm)).thenReturn(List.of(field));

        FieldCreationException exception = assertThrows(
                FieldCreationException.class,
                () -> service.addFields(farmResponseDto, List.of(oversizedField))
        );

        assertEquals("No field can exceed 50% of the farm's total area.", exception.getMessage());

        verify(repository, times(1)).findAllByFarm(farm);
        verify(mapper, never()).toEntity(any(FieldRequestDto.class));
    }

    @Test
    void findFieldById_success() {

        when(repository.findById(fieldId)).thenReturn(Optional.of(field));
        when(mapper.toDto(field)).thenReturn(fieldResponseDto);
        FieldResponseDto actualResponse = service.findFieldById(fieldId);

        assertEquals(fieldResponseDto, actualResponse);
        verify(repository, times(1)).findById(fieldId);
        verify(mapper, times(1)).toDto(field);
    }
    @Test
    void findFieldById_failure_fieldNotFound() {

        when(repository.findById(fieldId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> service.findFieldById(fieldId)
        );

        assertEquals("Field with id " + fieldId + " not found", exception.getMessage());
        verify(repository, times(1)).findById(fieldId);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void getAllFieldsByFarmId_success() {
        List<Field> fields = List.of(field,field);
        List<FieldResponseDto> fieldResponseDtos = List.of(fieldResponseDto,fieldResponseDto);

        when(farmMapper.toEntity(farmResponseDto)).thenReturn(farm);
        when(repository.findAllByFarm(farm)).thenReturn(fields);
        when(mapper.toDto(fields.get(0))).thenReturn(fieldResponseDtos.get(0));
        when(mapper.toDto(fields.get(1))).thenReturn(fieldResponseDtos.get(1));

        List<FieldResponseDto> actualResponse = service.getAllFieldsByFarmId(farmResponseDto);

        assertEquals(fieldResponseDtos, actualResponse);
        verify(farmMapper, times(1)).toEntity(farmResponseDto);
        verify(repository, times(1)).findAllByFarm(farm);
        verify(mapper, times(fields.size())).toDto(field);
    }

}

