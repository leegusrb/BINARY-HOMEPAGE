package com.binary.homepage.repository;

import com.binary.homepage.domain.Grass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GrassRepository {

    private final EntityManager em;

    public void save(Grass grass) {
        em.persist(grass);
    }

    public Grass findOne(Long id) {
        return em.find(Grass.class, id);
    }

    public List<Grass> findAll() {
        return em.createQuery("select g from Grass g", Grass.class)
                .getResultList();
    }
}
