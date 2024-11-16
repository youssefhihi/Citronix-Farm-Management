package com.ys.citronix.harvestManagement.infrastructure.repository;

import com.ys.citronix.harvestManagement.domain.model.Harvest;
import com.ys.citronix.harvestManagement.domain.valueObject.HarvestId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarvestRepository extends JpaRepository<Harvest, HarvestId> {
}
