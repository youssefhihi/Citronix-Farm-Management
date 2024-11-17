package com.ys.citronix.farmManagement.infrastructure.repository;

import com.ys.citronix.farmManagement.domain.model.Farm;
import com.ys.citronix.farmManagement.domain.model.Field;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID> {
    List<Field> findAllByFarm(Farm farm);
}
