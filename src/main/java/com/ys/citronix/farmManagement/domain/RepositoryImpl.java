package com.ys.citronix.farmManagement.domain;

import com.ys.citronix.farmManagement.domain.model.Farm;
import com.ys.citronix.farmManagement.infrastructure.repository.FarmSearchRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RepositoryImpl implements FarmSearchRepository {
    private final EntityManager em;

    @Override
    public List<Farm> findFarmMultiCriteriaSearch(String query){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Farm> cq = cb.createQuery(Farm.class);
        Root<Farm> farm = cq.from(Farm.class);
        Predicate name = cb.equal(farm.get("name"), query);
        Predicate location = cb.equal(farm.get("location"), query);
        Predicate area = cb.equal(farm.get("area"), query);
        cq.where(name, location, area);
        TypedQuery<Farm> q = em.createQuery(cq);
        return q.getResultList();
    }

}
