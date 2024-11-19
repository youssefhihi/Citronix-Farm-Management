package com.ys.citronix.harvestManagement.domain.service.impl;

import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.farmManagement.application.mapper.FarmMapper;
import com.ys.citronix.farmManagement.application.mapper.FieldMapper;
import com.ys.citronix.farmManagement.application.service.TreeApplicationService;
import com.ys.citronix.harvestManagement.application.dto.request.HarvestRequestDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;
import com.ys.citronix.harvestManagement.application.mapper.HarvestMapper;
import com.ys.citronix.harvestManagement.application.service.HarvestApplicationService;
import com.ys.citronix.harvestManagement.application.service.HarvestDetailsApplicationService;
import com.ys.citronix.harvestManagement.domain.events.HarvestCreatedEvent;
import com.ys.citronix.harvestManagement.domain.exception.HarvestCreationException;
import com.ys.citronix.harvestManagement.domain.model.Harvest;
import com.ys.citronix.harvestManagement.domain.service.HarvestService;
import com.ys.citronix.harvestManagement.infrastructure.repository.HarvestRepository;
import com.ys.citronix.sharedkernel.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HarvestDomainService implements HarvestService, HarvestApplicationService {
    private final HarvestRepository repository;
    private final HarvestMapper mapper;
    private final HarvestDetailsApplicationService harvestDetailsService;
    private final TreeApplicationService treeService;
    private final FieldMapper fieldMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final FarmMapper farmMapper;


    @Override
    public HarvestResponseDto createHarvest(HarvestRequestDto harvestRequestDto) {


        List<TreeResponseDto> trees = treeService.findAllTreesByIds(harvestRequestDto.treeId());

        if (trees.size() != harvestRequestDto.treeId().size()) {
            throw new HarvestCreationException("One or more Tree IDs were not found.");
        }

       boolean allTreesSameField = trees.stream()
               .allMatch(tree -> tree.field().equals(trees.get(0).field()));

        if(!allTreesSameField) {
            throw new HarvestCreationException("the trees doesn't have the same field");
        }

        if(harvestDetailsService.existsByTreeFieldSeasonAndYear(
                farmMapper.toEntity(trees.get(0).field().farm()),
                harvestRequestDto.season(),
                harvestRequestDto.harvestDate().getYear())
        ){
            throw new HarvestCreationException("A harvest already exists for this field in the same season.");
        }


        trees.stream()
                .filter(t -> harvestDetailsService.existsTreeInSeason(t.id(), harvestRequestDto.season()))
                .findFirst()
                .ifPresent(t -> {
                    throw new IllegalArgumentException("Tree " + t.id() + " has already been harvested this season.");
                });

        Harvest harvest = mapper.toEntity(harvestRequestDto);
        harvest.setTotalQuantity(trees.stream().mapToDouble(TreeResponseDto::ProductivityPerYear).sum());
        Harvest storedHarvest =  repository.save(harvest);
        eventPublisher.publishEvent(new HarvestCreatedEvent(mapper.toDto(storedHarvest), trees));

        return mapper.toDto(storedHarvest);
    }

    @Override
    public HarvestResponseDto findHarvestById(UUID harvestId){
        Harvest harvest = repository.findById(harvestId).orElseThrow(() -> new NotFoundException("harvest", harvestId));
        return mapper.toDto(harvest);
    }

}
