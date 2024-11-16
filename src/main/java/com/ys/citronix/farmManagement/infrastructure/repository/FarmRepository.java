package com.ys.citronix.farmManagement.infrastructure.repository;

import com.ys.citronix.farmManagement.domain.model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FarmRepository extends JpaRepository<Farm, UUID> {
}
