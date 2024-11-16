package com.ys.citronix.harvestManagement.domain.service.impl;

import com.ys.citronix.harvestManagement.domain.service.HarvestService;
import com.ys.citronix.harvestManagement.infrastructure.repository.HarvestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HarvestDomainService implements HarvestService {
    private final HarvestRepository repositoryepository;
}
