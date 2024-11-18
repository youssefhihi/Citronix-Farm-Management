package com.ys.citronix.harvestManagement.api;

import com.ys.citronix.harvestManagement.application.dto.response.HarvestDetailsResponseDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;
import com.ys.citronix.harvestManagement.application.service.HarvestApplicationService;
import com.ys.citronix.harvestManagement.domain.service.HarvestDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/harvest")
@RequiredArgsConstructor
@Validated
public class HarvestDetailsController {
    private final HarvestDetailsService service;
    private final HarvestApplicationService harvestService;


    @GetMapping("/{harvestId}")
    public ResponseEntity<List<HarvestDetailsResponseDto>> getHarvestDetailsByHarvest(@PathVariable String harvestId){
        HarvestResponseDto harvest = harvestService.findHarvestById(UUID.fromString(harvestId));
        List<HarvestDetailsResponseDto> responseDto = service.getHarvestDetailsByHarvest(harvest);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
