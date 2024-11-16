package com.ys.citronix.harvestManagement.domain.model;

import com.ys.citronix.harvestManagement.domain.enums.Season;
import com.ys.citronix.harvestManagement.domain.valueObject.HarvestId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Harvest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private HarvestId id;

    @NotNull(message = "harvest date must not be null")
    private LocalDateTime harvestDate;

    @Positive(message = "total quantity must be greater than zero")
    private Double totalQuantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Season season;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HarvestDetails> harvestDetails;

    @CreatedDate
    private LocalDateTime createdDate;
}
