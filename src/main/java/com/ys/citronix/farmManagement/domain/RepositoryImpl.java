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
    public List<Farm> findFarmMultiCriteriaSearch(String query) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Farm> cq = cb.createQuery(Farm.class);
        Root<Farm> farm = cq.from(Farm.class);
        Predicate namePredicate = cb.like(cb.lower(farm.get("name")), "%" + query.toLowerCase() + "%");
        Predicate locationPredicate = cb.like(cb.lower(farm.get("location")), "%" + query.toLowerCase() + "%");
        Predicate areaPredicate;
        try {
            Double areaValue = Double.parseDouble(query);
            areaPredicate = cb.equal(farm.get("area"), areaValue);
        } catch (NumberFormatException e) {
            areaPredicate = cb.disjunction();
        }
        cq.where(cb.or(namePredicate, locationPredicate, areaPredicate));
        TypedQuery<Farm> q = em.createQuery(cq);
        return q.getResultList();
    }

}
