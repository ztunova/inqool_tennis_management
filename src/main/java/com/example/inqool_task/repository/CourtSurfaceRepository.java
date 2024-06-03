package com.example.inqool_task.repository;

import com.example.inqool_task.data.Persistence;
import com.example.inqool_task.data.model.CourtSurface;
import com.example.inqool_task.data.model.Customer;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourtSurfaceRepository implements CrudRepositoryInterface<CourtSurface>{


    @Override
    public Optional<CourtSurface> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<CourtSurface> getAll() {

        return null;
    }

    @Override
    public CourtSurface save(CourtSurface courtSurface) {
        EntityManager em = Persistence.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(courtSurface);
            em.getTransaction().commit();
            return courtSurface;
        } catch (Exception e) {
            System.out.println("Persist operation of entity has failed due to: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public CourtSurface update(CourtSurface courtSurface) {
        return null;
    }

    @Override
    public void delete(CourtSurface courtSurface) {

    }
}
