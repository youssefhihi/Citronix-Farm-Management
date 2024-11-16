package com.ys.citronix.farmManagement.domain.service.impl;

import com.ys.citronix.farmManagement.domain.service.TreeService;
import com.ys.citronix.farmManagement.infrastructure.repository.TreeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TreeDomainService implements TreeService {
    private final TreeRepository repository;
}
