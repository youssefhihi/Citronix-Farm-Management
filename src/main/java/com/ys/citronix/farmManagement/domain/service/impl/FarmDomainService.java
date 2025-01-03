package com.ys.citronix.farmManagement.domain.service.impl;

import com.ys.citronix.farmManagement.application.service.FarmApplicationService;
import com.ys.citronix.farmManagement.application.dto.request.FarmRequestDto;
import com.ys.citronix.farmManagement.application.dto.request.FarmRequestUpdateDto;
import com.ys.citronix.farmManagement.application.dto.response.FarmResponseDto;
import com.ys.citronix.farmManagement.application.mapper.FarmMapper;
import com.ys.citronix.farmManagement.domain.model.Farm;
import com.ys.citronix.farmManagement.domain.service.FarmService;
import com.ys.citronix.farmManagement.infrastructure.repository.FarmRepository;
import com.ys.citronix.farmManagement.infrastructure.repository.FarmSearchRepository;
import com.ys.citronix.sharedkernel.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FarmDomainService implements FarmService , FarmApplicationService {
    private final FarmRepository repository;
    private final FarmSearchRepository farmSearchRepository;
    private final FarmMapper mapper;

    @Override
    public Page<FarmResponseDto> getAllFarms(Pageable pageable) {
        Page<Farm> farmsPage = repository.findAll(pageable);
        return farmsPage.map(mapper::toDto);
    }

    @Override
    public FarmResponseDto createFarm(FarmRequestDto farmRequestDto) {

        Farm farm = Farm.builder()
                .area(farmRequestDto.area())
                .createdDateTime(farmRequestDto.createdDateTime())
                .location(farmRequestDto.location())
                .name(farmRequestDto.name())
                .build();

        return mapper.toDto(repository.save(farm));
    }

    @Override
    public FarmResponseDto updateFarm(FarmRequestUpdateDto farmRequestDto) {
        if (!repository.existsById(farmRequestDto.id())){
            throw new NotFoundException("Farm", farmRequestDto.id());
        }
        Farm farm = repository.save(mapper.toEntity(farmRequestDto));
        return mapper.toDto(farm) ;
    }

    @Override
    public void deleteFarm(UUID farmId) {
        if (!repository.existsById(farmId)){
            throw new NotFoundException("Farm", farmId);
        }
        repository.deleteById(farmId);
    }

    @Override
    public FarmResponseDto findFarmById(UUID id) {
        Farm farm = repository.findById(id).orElseThrow(() -> new NotFoundException("Farm", id));
        return mapper.toDto(farm);
    }

    @Override
    public List<FarmResponseDto>  findFarmMultiCriteriaSearch(String query){
        List<Farm> farms = farmSearchRepository.findFarmMultiCriteriaSearch(query);
        return farms.stream().map(mapper::toDto).toList();
    }

}
