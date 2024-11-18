package com.ys.citronix.harvestManagement.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ys.citronix.farmManagement.domain.model.Field;
import com.ys.citronix.harvestManagement.domain.enums.Season;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Harvest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "harvest date must not be null")
    private LocalDateTime harvestDate;

    @Positive(message = "total quantity must be greater than zero")
    private Double totalQuantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Season season;

    @ManyToOne
    @JoinColumn(name = "field_id")
    @JsonIgnore
    private Field field;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<HarvestDetails> harvestDetails;

    @CreatedDate
    private LocalDateTime createdDate;
}
