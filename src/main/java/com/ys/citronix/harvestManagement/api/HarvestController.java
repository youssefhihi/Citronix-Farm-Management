package com.ys.citronix.harvestManagement.api;

import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.application.service.FieldApplicationService;
import com.ys.citronix.harvestManagement.application.dto.request.HarvestRequestDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;
import com.ys.citronix.harvestManagement.domain.service.HarvestService;
import com.ys.citronix.sharedkernel.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/harvest")
@RequiredArgsConstructor
@Validated
public class HarvestController {
    private final HarvestService service;
    private final FieldApplicationService fieldService;

    @PostMapping("/{fieldId}")
    public ResponseEntity<ApiResponse<HarvestResponseDto>> createHarvest(
            @PathVariable String fieldId,
            @RequestBody @Valid HarvestRequestDto harvestRequestDto){
        FieldResponseDto field =  fieldService.findFieldById(UUID.fromString(fieldId));
        HarvestResponseDto responseDto = service.createHarvest(field, harvestRequestDto);
        ApiResponse<HarvestResponseDto> response = new ApiResponse<>(
                responseDto,
                "harvest created successfully",
                HttpStatus.CREATED
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
