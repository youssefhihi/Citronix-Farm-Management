package com.ys.citronix.saleManagement.serviceTest;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;
import com.ys.citronix.harvestManagement.application.mapper.HarvestMapper;
import com.ys.citronix.harvestManagement.domain.enums.Season;
import com.ys.citronix.harvestManagement.domain.model.Harvest;
import com.ys.citronix.salesManagement.application.dto.request.SaleRequestDto;
import com.ys.citronix.salesManagement.application.dto.response.SaleResponseDto;
import com.ys.citronix.salesManagement.application.mapper.SaleMapper;
import com.ys.citronix.salesManagement.domain.exception.SaleCreationException;
import com.ys.citronix.salesManagement.domain.model.Sale;
import com.ys.citronix.salesManagement.domain.service.impl.SaleDomainService;
import com.ys.citronix.salesManagement.infrastructure.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository repository;

    @Mock
    private SaleMapper mapper;

    @Mock
    private HarvestMapper harvestMapper;

    @InjectMocks
    private SaleDomainService service;

    private HarvestResponseDto harvestResponseDto;
    private SaleRequestDto saleRequestDto;
    private Sale sale;
    private SaleResponseDto saleResponseDto;

    @BeforeEach
    void setUp() {
        harvestResponseDto = new HarvestResponseDto(UUID.randomUUID(), LocalDateTime.now(), 100.0, Season.Spring);
        saleRequestDto = new SaleRequestDto(LocalDateTime.now(), "Client1", 50.0, 10.0);
        sale = new Sale();
        sale.setQuantitySold(50.0);
        saleResponseDto = new SaleResponseDto(UUID.randomUUID(), LocalDateTime.now(),"client", 1000.0,13.0,122.0,harvestResponseDto);
    }

    @Test
    void createSale_success() {
        List<Sale> existingSales = List.of(sale);
        when(repository.getAllByHarvest_Id(harvestResponseDto.id())).thenReturn(existingSales);
        when(mapper.toEntity(saleRequestDto)).thenReturn(sale);
        when(harvestMapper.toEntity(harvestResponseDto)).thenReturn(new Harvest());
        when(repository.save(sale)).thenReturn(sale);
        when(mapper.toDto(sale)).thenReturn(saleResponseDto);

        SaleResponseDto result = service.createSale(harvestResponseDto, saleRequestDto);

        assertEquals(saleResponseDto, result);
        verify(repository, times(1)).getAllByHarvest_Id(harvestResponseDto.id());
        verify(repository, times(1)).save(sale);
        verify(mapper, times(1)).toDto(sale);
    }

    @Test
    void createSale_insufficientQuantity() {

        List<Sale> existingSales = List.of(sale);
        when(repository.getAllByHarvest_Id(harvestResponseDto.id())).thenReturn(existingSales);

        saleRequestDto = new SaleRequestDto(LocalDateTime.now(), "Client1", 60.0, 10.0);

        SaleCreationException exception = assertThrows(SaleCreationException.class, () -> {
            service.createSale(harvestResponseDto, saleRequestDto);
        });

        assertEquals("Insufficient quantity in harvest. Available: 50.0kg.", exception.getMessage());
    }




    @Test
    void getAllSalesByHarvest_success() {

        List<Sale> sales = List.of(sale);
        when(repository.getAllByHarvest_Id(harvestResponseDto.id())).thenReturn(sales);
        when(mapper.toDto(sale)).thenReturn(saleResponseDto);

        List<SaleResponseDto> result = service.getAllSalesByHarvest(harvestResponseDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(saleResponseDto, result.get(0));
        verify(repository, times(1)).getAllByHarvest_Id(harvestResponseDto.id());
    }

    @Test
    void getAllSalesByHarvest_noSales() {

        when(repository.getAllByHarvest_Id(harvestResponseDto.id())).thenReturn(List.of());

        List<SaleResponseDto> result = service.getAllSalesByHarvest(harvestResponseDto);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).getAllByHarvest_Id(harvestResponseDto.id());
    }
}
