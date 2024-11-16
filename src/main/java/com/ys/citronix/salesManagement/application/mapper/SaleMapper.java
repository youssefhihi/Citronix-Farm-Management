package com.ys.citronix.salesManagement.application.mapper;

import com.ys.citronix.salesManagement.application.dto.request.SaleRequestDto;
import com.ys.citronix.salesManagement.application.dto.response.SaleResponseDto;
import com.ys.citronix.salesManagement.domain.model.Sale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    Sale toEntity(SaleRequestDto saleRequestDto);
    SaleResponseDto toDto(Sale sale);
}
