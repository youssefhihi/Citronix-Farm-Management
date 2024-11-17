package com.ys.citronix.farmManagement.domain.service.impl;

import com.ys.citronix.farmManagement.application.dto.request.FieldRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.FarmResponseDto;
import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.application.mapper.FarmMapper;
import com.ys.citronix.farmManagement.application.mapper.FieldMapper;
import com.ys.citronix.farmManagement.application.service.FieldApplicationService;
import com.ys.citronix.farmManagement.domain.exception.FieldCreationException;
import com.ys.citronix.farmManagement.domain.model.Field;
import com.ys.citronix.farmManagement.domain.service.FieldService;
import com.ys.citronix.farmManagement.infrastructure.repository.FieldRepository;
import com.ys.citronix.sharedkernel.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FieldDomainService implements FieldService , FieldApplicationService {
    private final FieldRepository repository;
    private final FieldMapper mapper;
    private final FarmMapper farmMapper;

    @Override
    public List<FieldResponseDto> addFields(FarmResponseDto farm, List<FieldRequestDto> fieldsRequestDto) {

        validateFields(farm, fieldsRequestDto);

        List<Field> fields = fieldsRequestDto.stream()
                .map(mapper::toEntity)
                .toList();

        fields.forEach(field -> field.setFarm(farmMapper.toEntity(farm)));

        List<Field> storedFields = repository.saveAll(fields);

        return storedFields.stream().map(mapper::toDto).toList();
    }


    @Override
    public FieldResponseDto findFieldById(UUID id){
        Field field = repository.findById(id).orElseThrow(() -> new NotFoundException("Field", id));
        return mapper.toDto(field);
    }


    @Override
    public List<FieldResponseDto> getAllFieldsByFarmId(FarmResponseDto farm) {
        List<Field> fields = repository.findAllByFarm(farmMapper.toEntity(farm));
        return fields.stream().map(mapper::toDto).toList();
    }



    private void validateFields(FarmResponseDto farm, List<FieldRequestDto> fieldsRequestDto) {
        List<Field> oldFields = repository.findAllByFarm(farmMapper.toEntity(farm));

        double totalOldFieldArea = oldFields.stream()
                .mapToDouble(Field::getArea)
                .sum();

        double totalFieldArea = fieldsRequestDto.stream()
                .mapToDouble(FieldRequestDto::area)
                .sum();

        if (totalFieldArea + totalOldFieldArea >= farm.area()) {
            throw new FieldCreationException("Total area of fields cannot be greater than or equal to the farm area.");
        }

        if (fieldsRequestDto.size() + oldFields.size() > 10) {
            throw new FieldCreationException("A farm cannot have more than 10 fields.");
        }

        for (FieldRequestDto field : fieldsRequestDto) {
            double fieldArea = field.area();

            if (fieldArea < 1000) {
                throw new FieldCreationException( "Area of " +field.name() + " field must be at least 1000 m² (0.1 hectare).");
            }

            if (fieldArea > (farm.area() / 2)) {
                throw new FieldCreationException("No field can exceed 50% of the farm's total area.");
            }
        }
    }


}
