package com.example.inqool_task.repository;

import com.example.inqool_task.data.Persistence;
import com.example.inqool_task.data.model.CourtSurface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CrudRepository {
    private static final Logger log = LoggerFactory.getLogger(CrudRepository.class);

    public <R> R create(R objToCreate) {
        EntityManager em = Persistence.getEntityManager();
        // R entity = null;
        try {
            em.getTransaction().begin();
            // entity = createFunction.get();
            em.persist(objToCreate);
            em.getTransaction().commit();
            log.info("New entity '" + objToCreate.getClass().getSimpleName() + "' created: " + objToCreate);
            return objToCreate;
        }
        catch (Exception e) {
            log.error("Persist operation of entity '" + (objToCreate != null ? objToCreate.getClass().getSimpleName() : "unknown") + "' has failed due to: " + e.getMessage(), e);
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            return null;
        }
        finally {
            em.close();
        }
    }

}
