package com.ys.citronix.harvestManagement.infrastructure.repository;

import com.ys.citronix.farmManagement.domain.model.Field;
import com.ys.citronix.harvestManagement.domain.enums.Season;
import com.ys.citronix.harvestManagement.domain.model.Harvest;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest, UUID> {

    Boolean existsByFieldAndSeason(Field field, @NotNull Season season);
}
