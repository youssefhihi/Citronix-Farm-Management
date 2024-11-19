package com.ys.citronix.salesManagement.infrastructure.repository;

import com.ys.citronix.salesManagement.domain.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {
}
