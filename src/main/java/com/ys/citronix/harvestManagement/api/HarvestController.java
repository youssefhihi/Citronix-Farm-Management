package com.ys.citronix.harvestManagement.api;

import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.application.service.FieldApplicationService;
import com.ys.citronix.harvestManagement.application.dto.request.HarvestRequestDto;
import com.ys.citronix.harvestManagement.application.dto.request.HarvestUpdateRequestDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestDetailsResponseDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;
import com.ys.citronix.harvestManagement.domain.service.HarvestService;
import com.ys.citronix.sharedkernel.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/harvest")
@RequiredArgsConstructor
@Validated
public class HarvestController {
    private final HarvestService service;

    @PostMapping
    public ResponseEntity<ApiResponse<HarvestResponseDto>> createHarvest(
            @RequestBody @Valid HarvestRequestDto harvestRequestDto){
        HarvestResponseDto responseDto = service.createHarvest(harvestRequestDto);
        ApiResponse<HarvestResponseDto> response = new ApiResponse<>(
                responseDto,
                "harvest created successfully",
                HttpStatus.CREATED
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/addTrees")
    public ResponseEntity<ApiResponse<HarvestResponseDto>> updateHarvest(
            @RequestBody @Valid HarvestUpdateRequestDto harvestRequestDto
    ){
        HarvestResponseDto responseDto = service.updateHarvest(harvestRequestDto);
        ApiResponse<HarvestResponseDto> response = new ApiResponse<>(
                responseDto,
                "Harvest Updated Successfully",
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHarvest(@PathVariable  String id){
        service.deleteHarvest(UUID.fromString(id));
        return new ResponseEntity<>("Harvest Deleted Successfully",HttpStatus.OK);
    }


}
