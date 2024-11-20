package com.ys.citronix.farmManagement.api;

import com.ys.citronix.farmManagement.application.dto.request.FarmRequestDto;
import com.ys.citronix.farmManagement.application.dto.request.FarmRequestUpdateDto;
import com.ys.citronix.farmManagement.application.dto.response.FarmResponseDto;
import com.ys.citronix.farmManagement.domain.service.FarmService;
import com.ys.citronix.sharedkernel.api.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/farm")
@RequiredArgsConstructor
@Validated
public class FarmController {
    private final FarmService service;

    @GetMapping
    public ResponseEntity<List<FarmResponseDto>> getAllFarms() {
        List<FarmResponseDto> farms = service.getAllFarms();
        return new ResponseEntity<>(farms, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FarmResponseDto>> getFarmByQuery(@Valid @NotBlank @RequestParam String query) {
        List<FarmResponseDto> farms = service.findFarmMultiCriteriaSearch(query);
        return new ResponseEntity<>(farms, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FarmResponseDto>> createFarm(@Valid @RequestBody FarmRequestDto farmRequestDto) {
        FarmResponseDto responseDto = service.createFarm(farmRequestDto);
        ApiResponse<FarmResponseDto> apiResponse = new ApiResponse<>(
                responseDto,
                "Farm Created Successfully",
                HttpStatus.CREATED
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<FarmResponseDto>> createFarm(@Valid @RequestBody FarmRequestUpdateDto farmRequestDto) {
        FarmResponseDto responseDto = service.updateFarm(farmRequestDto);
        ApiResponse<FarmResponseDto> apiResponse = new ApiResponse<>(
                responseDto,
                "Farm Updated Successfully",
                HttpStatus.ACCEPTED
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{farmId}")
    public ResponseEntity<String> deleteFarm(@PathVariable String farmId) {
        service.deleteFarm(UUID.fromString(farmId));
        return new ResponseEntity<>("Farm Deleted Successfully", HttpStatus.OK);
    }
}
