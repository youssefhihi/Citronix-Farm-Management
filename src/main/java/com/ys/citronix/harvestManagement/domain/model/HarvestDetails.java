package com.ys.citronix.harvestManagement.domain.model;

import com.ys.citronix.farmManagement.domain.model.Tree;
import com.ys.citronix.harvestManagement.domain.valueObject.HarvestDetailsId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class HarvestDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private HarvestDetailsId id;

    @Positive(message = "Harvest quantity must be greater than zero")
    private Double quantity;

    @ManyToOne
    @JoinColumn(name = "harvest_id")
    private Harvest harvest;

    @ManyToOne
    @JoinColumn(name = "tree_id")
    private Tree tree;

    @CreatedDate
    private LocalDateTime createdDate;

}
