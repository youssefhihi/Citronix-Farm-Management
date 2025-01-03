package com.ys.citronix.harvestManagement.infrastructure.repository;

import com.ys.citronix.farmManagement.domain.model.Farm;
import com.ys.citronix.farmManagement.domain.model.Field;
import com.ys.citronix.harvestManagement.domain.enums.Season;
import com.ys.citronix.harvestManagement.domain.model.Harvest;
import com.ys.citronix.harvestManagement.domain.model.HarvestDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface HarvestDetailsRepository extends JpaRepository<HarvestDetails, UUID> {
    Boolean existsByTree_IdAndHarvest_Season(UUID treeId, Season season);
    List<HarvestDetails> findAllByTree_Field_FarmAndHarvest_Season(Farm farm, Season season);
    List<HarvestDetails> findAllByTree_Field(Field field);
    List<HarvestDetails> findAllByHarvest(Harvest harvest);

}
