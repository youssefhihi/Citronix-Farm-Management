package com.ys.citronix.harvestManagement.application.service;

import com.ys.citronix.harvestManagement.domain.enums.Season;

import java.util.UUID;

public interface HarvestDetailsApplicationService {
    Boolean existsTreeInSeason(UUID treeId, Season season);
}
