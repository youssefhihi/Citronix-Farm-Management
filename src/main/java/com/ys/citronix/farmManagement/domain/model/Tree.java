package com.ys.citronix.farmManagement.domain.model;

import com.ys.citronix.farmManagement.domain.valueObject.TreeId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Entity
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private LocalDateTime plantingDate;

    @Positive(message = "tree age must greater than zero")
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;


}
