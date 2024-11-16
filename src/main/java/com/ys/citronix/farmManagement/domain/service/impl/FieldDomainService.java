package com.ys.citronix.farmManagement.domain.service.impl;

import com.ys.citronix.farmManagement.domain.service.FieldService;
import com.ys.citronix.farmManagement.infrastructure.repository.FieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldDomainService implements FieldService {
    private final FieldRepository repository;
}
