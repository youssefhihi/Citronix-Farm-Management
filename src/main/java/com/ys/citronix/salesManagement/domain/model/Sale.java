package com.ys.citronix.salesManagement.domain.model;

import com.ys.citronix.harvestManagement.domain.model.Harvest;
import com.ys.citronix.salesManagement.domain.valueObject.SaleId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private SaleId id;

    @NotNull(message = "Sale date must not be null")
    private LocalDateTime saleDate;

    @NotNull(message = "Client name must not be null")
    private String clientName;

    @Positive(message = "Quantity sold must be greater than zero")
    private Double quantitySold;

    @Positive(message = "Price per unit must be greater than zero")
    private Double pricePerUnit;

    @ManyToOne
    @JoinColumn(name = "harvest_id", nullable = false)
    private Harvest harvest;

    @CreatedDate
    private LocalDateTime createdDate;
}