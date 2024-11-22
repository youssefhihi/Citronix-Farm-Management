package com.ys.citronix.salesManagement.api;

import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;
import com.ys.citronix.harvestManagement.application.service.HarvestApplicationService;
import com.ys.citronix.salesManagement.application.dto.request.SaleRequestDto;
import com.ys.citronix.salesManagement.application.dto.request.SaleUpdateRequestDto;
import com.ys.citronix.salesManagement.application.dto.response.SaleResponseDto;
import com.ys.citronix.salesManagement.domain.service.SaleService;
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
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
@Validated
public class SaleController {
    private final SaleService service;
    private final HarvestApplicationService harvestService;


    @PostMapping("/{harvestId}")
    public ResponseEntity<ApiResponse<SaleResponseDto>> createSale(
            @PathVariable String harvestId,
            @Valid @RequestBody SaleRequestDto dto) {
        HarvestResponseDto harvest = harvestService.findHarvestById(UUID.fromString(harvestId));
        SaleResponseDto sale = service.createSale(harvest,dto);
        ApiResponse<SaleResponseDto> response = new ApiResponse<>(
                sale,
                "Sale created Successfully",
                HttpStatus.CREATED
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{harvestId}")
    public ResponseEntity<List<SaleResponseDto>> getSale(@PathVariable String harvestId) {
        HarvestResponseDto harvest = harvestService.findHarvestById(UUID.fromString(harvestId));
        List<SaleResponseDto> response = service.getAllSalesByHarvest(harvest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<SaleResponseDto>> updateSale(@RequestBody @Valid SaleUpdateRequestDto dto){
        SaleResponseDto responseDto = service.updateSale(dto);
        ApiResponse<SaleResponseDto> response = new ApiResponse<>(
                responseDto,
                "Sale Updated Successfully",
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteSale(@PathVariable String id) {
        service.deleteSale(UUID.fromString(id));
        return new ResponseEntity<>("Sale Deleted Successfully", HttpStatus.OK);
    }
}
