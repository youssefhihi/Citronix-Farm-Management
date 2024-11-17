package com.ys.citronix.farmManagement.infrastructure.repository;

import com.ys.citronix.farmManagement.domain.model.Field;
import com.ys.citronix.farmManagement.domain.model.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TreeRepository extends JpaRepository<Tree, UUID> {
    List<Tree> findAllByField(Field field);
    Integer countByField(Field field);

}
