package com.ys.citronix.farmManagement.domain.service.impl;

import com.ys.citronix.farmManagement.domain.service.FarmService;
import com.ys.citronix.farmManagement.infrastructure.repository.FarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FarmDomainService implements FarmService {
    private final FarmRepository repository;
}
