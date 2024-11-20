package com.ys.citronix.farmManagement.infrastructure.repository;

import com.ys.citronix.farmManagement.domain.model.Farm;

import java.util.List;

public interface FarmSearchRepository {
    List<Farm> findFarmMultiCriteriaSearch(String query);
}
