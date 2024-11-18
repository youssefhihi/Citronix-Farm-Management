package com.ys.citronix.harvestManagement.infrastructure.repository;

import com.ys.citronix.harvestManagement.domain.enums.Season;
import com.ys.citronix.harvestManagement.domain.model.HarvestDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HarvestDetailsRepository extends JpaRepository<HarvestDetails, UUID> {
    Boolean existsByTree_IdAndHarvest_Season(UUID treeId, Season season);
}
