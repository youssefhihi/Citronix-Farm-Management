package com.ys.citronix.farmManagement.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ys.citronix.farmManagement.domain.valueObject.TreeId;
import com.ys.citronix.harvestManagement.domain.model.HarvestDetails;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private LocalDateTime plantingDate;


    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;
    @CreatedDate
    private LocalDateTime createdDate;


    @OneToMany(mappedBy = "tree", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<HarvestDetails> harvestDetails;

    public Tree() {}

    public Integer getAge(){
        return (int) ChronoUnit.YEARS.between(this.getPlantingDate().toLocalDate(), LocalDate.now());
    }

    public Double getAnnualProductivity() {
        int age = this.getAge();
        if (age < 3) return 2.5;
        else if (age <= 10) return 12.0;
        else if(age <= 20 ) return 20.0;
        else return 0.0;
    }


}
