package com.ys.citronix.salesManagement.application.mapper;

import com.ys.citronix.harvestManagement.application.mapper.HarvestMapper;
import com.ys.citronix.salesManagement.application.dto.request.SaleRequestDto;
import com.ys.citronix.salesManagement.application.dto.response.SaleResponseDto;
import com.ys.citronix.salesManagement.domain.model.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = HarvestMapper.class)
public interface SaleMapper {
    Sale toEntity(SaleRequestDto saleRequestDto);
    @Mapping(target = "harvest", source = "harvest")
    @Mapping(target = "revenue", expression = "java(sale.getQuantitySold() * sale.getPricePerUnit())")
    SaleResponseDto toDto(Sale sale);
    Sale toEntity(SaleResponseDto sale);

}
