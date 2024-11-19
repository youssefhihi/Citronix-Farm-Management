package com.ys.citronix.salesManagement.domain.service;

import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;
import com.ys.citronix.salesManagement.application.dto.request.SaleRequestDto;
import com.ys.citronix.salesManagement.application.dto.response.SaleResponseDto;

public interface SaleService {
    SaleResponseDto createSale(HarvestResponseDto harvest, SaleRequestDto saleRequestDto);
}
