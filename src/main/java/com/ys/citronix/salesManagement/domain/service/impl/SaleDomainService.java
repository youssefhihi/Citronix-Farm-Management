package com.ys.citronix.salesManagement.domain.service.impl;

import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;
import com.ys.citronix.harvestManagement.application.mapper.HarvestMapper;
import com.ys.citronix.salesManagement.application.dto.request.SaleRequestDto;
import com.ys.citronix.salesManagement.application.dto.response.SaleResponseDto;
import com.ys.citronix.salesManagement.application.mapper.SaleMapper;
import com.ys.citronix.salesManagement.domain.exception.SaleCreationException;
import com.ys.citronix.salesManagement.domain.model.Sale;
import com.ys.citronix.salesManagement.domain.service.SaleService;
import com.ys.citronix.salesManagement.infrastructure.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleDomainService implements SaleService {
    private final SaleRepository repository;
    private final SaleMapper mapper;
    private final HarvestMapper harvestMapper;


    @Override
    public SaleResponseDto createSale(HarvestResponseDto harvest, SaleRequestDto saleRequestDto) {
        List<Sale> sales = repository.getAllByHarvest_Id(harvest.id());

        double totalQuantitySold = sales.stream().mapToDouble(Sale::getQuantitySold).sum();

        double availableQuantity = harvest.totalQuantity() - totalQuantitySold;
        if (saleRequestDto.quantitySold() > availableQuantity) {
            throw new SaleCreationException(
                    "Insufficient quantity in harvest. Available: " + availableQuantity + "kg."
            );
        }

        Sale sale = mapper.toEntity(saleRequestDto);
        sale.setHarvest(harvestMapper.toEntity(harvest));

        return mapper.toDto(repository.save(sale));
    }


    @Override
    public List<SaleResponseDto> getAllSalesByHarvest(HarvestResponseDto harvest) {
        List<Sale> sales = repository.getAllByHarvest_Id(harvest.id());
        return sales.stream().map(mapper::toDto).toList();
    }

}
