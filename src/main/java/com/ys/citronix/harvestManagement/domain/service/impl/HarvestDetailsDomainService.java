package com.ys.citronix.harvestManagement.domain.service.impl;

import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.farmManagement.application.mapper.TreeMapper;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestDetailsResponseDto;
import com.ys.citronix.harvestManagement.application.mapper.HarvestDetailsMapper;
import com.ys.citronix.harvestManagement.application.service.HarvestDetailsApplicationService;
import com.ys.citronix.harvestManagement.domain.enums.Season;
import com.ys.citronix.harvestManagement.domain.events.HarvestCreatedEvent;
import com.ys.citronix.harvestManagement.domain.model.Harvest;
import com.ys.citronix.harvestManagement.domain.model.HarvestDetails;
import com.ys.citronix.harvestManagement.domain.service.HarvestDetailsService;
import com.ys.citronix.harvestManagement.infrastructure.repository.HarvestDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HarvestDetailsDomainService implements HarvestDetailsService, HarvestDetailsApplicationService {
    private final HarvestDetailsRepository repository;
    private final HarvestDetailsMapper mapper;
    private final TreeMapper treeMapper;


    @EventListener
    public List<HarvestDetailsResponseDto> createHarvestDetails(HarvestCreatedEvent event) {
        Harvest harvest = event.harvest();
        List<TreeResponseDto> trees = event.trees();

        List<HarvestDetails> harvestDetailsList = trees.stream()
                .map(tree -> {
                    HarvestDetails harvestDetail = new HarvestDetails();
                    harvestDetail.setHarvest(harvest);
                    harvestDetail.setTree(treeMapper.toEntity(tree));
                    harvestDetail.setQuantity(tree.ProductivityPerYear());
                    harvest.setCreatedDate(LocalDateTime.now());
                    return harvestDetail;
                })
                .collect(Collectors.toList());

        List<HarvestDetails> savedDetails = repository.saveAll(harvestDetailsList);

        return savedDetails.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean existsTreeInSeason(UUID treeId, Season season) {
        return repository.existsByTree_IdAndHarvest_Season(treeId, season);
    }
}