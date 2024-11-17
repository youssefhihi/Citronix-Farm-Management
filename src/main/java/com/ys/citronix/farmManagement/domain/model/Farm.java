package com.ys.citronix.farmManagement.domain.model;

import com.ys.citronix.farmManagement.domain.valueObject.FarmId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = " farm name must not be blank")
    @Size(min = 3, max = 100, message = "Farm name must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = " farm location must not be blank")
    @Size(min = 2, max = 100, message = "Farm name must be between 3 and 100 characters")
    private String location;

    @Positive(message = " farm area must be greater than zero")
    private Double area;

    @NotNull(message = "date create of farm is required")
    private LocalDateTime createdDateTime;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Field> fields = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdDate;

}
