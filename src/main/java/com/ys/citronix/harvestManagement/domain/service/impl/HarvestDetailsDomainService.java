package com.ys.citronix.harvestManagement.domain.service.impl;

import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.farmManagement.application.mapper.TreeMapper;
import com.ys.citronix.farmManagement.domain.model.Farm;
import com.ys.citronix.farmManagement.domain.model.Field;
import com.ys.citronix.harvestManagement.application.dto.request.HarvestDetailsRequestDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestDetailsResponseDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;
import com.ys.citronix.harvestManagement.application.mapper.HarvestDetailsMapper;
import com.ys.citronix.harvestManagement.application.mapper.HarvestMapper;
import com.ys.citronix.harvestManagement.application.service.HarvestDetailsApplicationService;
import com.ys.citronix.harvestManagement.domain.enums.Season;
import com.ys.citronix.harvestManagement.domain.model.HarvestDetails;
import com.ys.citronix.harvestManagement.domain.service.HarvestDetailsService;
import com.ys.citronix.harvestManagement.infrastructure.repository.HarvestDetailsRepository;
import com.ys.citronix.sharedkernel.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HarvestDetailsDomainService implements HarvestDetailsService, HarvestDetailsApplicationService {
    private final HarvestDetailsRepository repository;
    private final HarvestDetailsMapper mapper;
    private final TreeMapper treeMapper;
    private final HarvestMapper harvestMapper;


    @Override
    public void createHarvestDetails(HarvestDetailsRequestDto harvestDetailsRequestDto) {
        HarvestResponseDto harvest = harvestDetailsRequestDto.harvest();
        List<TreeResponseDto> trees = harvestDetailsRequestDto.trees();

        List<HarvestDetails> harvestDetailsList = trees.stream()
                .map(tree -> {
                    HarvestDetails harvestDetail = new HarvestDetails();
                    harvestDetail.setHarvest(harvestMapper.toEntity(harvest));
                    harvestDetail.setTree(treeMapper.toEntity(tree));
                    harvestDetail.setQuantity(tree.ProductivityPerYear());
                    harvestDetail.setCreatedDate(LocalDateTime.now());
                    return harvestDetail;
                })
                .collect(Collectors.toList());

        List<HarvestDetails> savedDetails = repository.saveAll(harvestDetailsList);

        savedDetails.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Boolean existsTreeInSeason(UUID treeId, Season season) {
        return repository.existsByTree_IdAndHarvest_Season(treeId, season);
    }

    @Override
    public Boolean existsByTreeFieldSeasonAndYear(Farm farm, Season season, Integer year) {
        List<HarvestDetails> details = repository.findAllByTree_Field_FarmAndHarvest_Season(farm, season);
        return details.stream()
                .anyMatch(hd -> hd.getHarvest().getHarvestDate().getYear() == year);
    }


    @Override
    public List<HarvestDetailsResponseDto> getHarvestDetailsByHarvest(HarvestResponseDto harvest) {
      List<HarvestDetails> harvestDetails =  repository.findAllByHarvest(harvestMapper.toEntity(harvest));
      return harvestDetails.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteHarvestDetailsById(UUID id){
        if (!repository.existsById(id)){
            throw new NotFoundException("Harvest details",id);
        }
        repository.deleteById(id);
    }

}
