package com.ys.citronix.salesManagement.domain.service;

import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;
import com.ys.citronix.salesManagement.application.dto.request.SaleRequestDto;
import com.ys.citronix.salesManagement.application.dto.request.SaleUpdateRequestDto;
import com.ys.citronix.salesManagement.application.dto.response.SaleResponseDto;

import java.util.List;
import java.util.UUID;

public interface SaleService {
    SaleResponseDto createSale(HarvestResponseDto harvest, SaleRequestDto saleRequestDto);
    List<SaleResponseDto> getAllSalesByHarvest(HarvestResponseDto harvest);
    SaleResponseDto updateSale(SaleUpdateRequestDto dto);
    void deleteSale(UUID id);
}
