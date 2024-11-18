package com.ys.citronix.farmManagement.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ys.citronix.farmManagement.domain.valueObject.FieldId;
import com.ys.citronix.harvestManagement.domain.model.Harvest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "field name must not be blank")
    @Size(min = 3, max = 100, message = "field name must be between 3 and 100 characters")
    private String name;

    @Positive(message = "the farm area must be greater than zero")
    private Double area;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToMany(mappedBy = "field")
    @JsonIgnore
    private List<Tree> trees = new ArrayList<>();

    @OneToMany(mappedBy = "field")
    @JsonIgnore
    private List<Harvest> harvests = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdDate;
}
