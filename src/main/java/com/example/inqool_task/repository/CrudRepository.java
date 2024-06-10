package com.example.inqool_task.repository;

//import com.example.inqool_task.HibernateUtil;
import com.example.inqool_task.data.Persistence;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class CrudRepository {

    private static final Logger log = LoggerFactory.getLogger(CrudRepository.class);

    public <R> R create(R objToCreate) {
        EntityManager em = Persistence.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(objToCreate);
            em.getTransaction().commit();
            log.info("New entity '" + objToCreate.getClass().getSimpleName() + "' created: " + objToCreate);
            return objToCreate;
        }
        catch (Exception e) {
            log.error("Persist operation of entity '" + (objToCreate != null ? objToCreate.getClass().getSimpleName() : "unknown") + "' has failed due to: " + e.getMessage(), e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        }
        finally {
            em.close();
        }
    }

    public <R> R getById(Long id, Class<R> clazz) {
        if (id == null || clazz == null) {
            return null;
        }
        EntityManager em = Persistence.getEntityManager();
        try {
            return em.find(clazz, id);
        } catch (Exception e) {
            log.error("Could not find any entity '" + clazz.getSimpleName() + "' with id '" + id + "'. " + e.getMessage(), e);
            return null;
        } finally {
            em.close();
        }
    }

//    public <R> List<R> criteriaFindAll(Class<R> clazz) {
//        EntityManager em = Persistence.getEntityManager();
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<R> query = cb.createQuery(clazz);
//
//        //TODO under this line create a Root<Product> instance and then use .select() method on this instance
//        Root<R> personRoot = query.from(clazz);
//        query.select(personRoot).where(cb.equal(personRoot.get(clazz.), "John Doe"));
//
//        return em.createQuery(query).getResultList();
//    }

    public <R> R update(R entity) {
        if (entity == null) {
            return null;
        }
        EntityManager em = Persistence.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.flush();
            em.getTransaction().commit();
            log.info("Entity '" + entity.getClass().getSimpleName() + "' was successfully updated.");
            return entity;
        } catch (Exception e) {
            log.error("Update operation of entity '" + entity.getClass().getSimpleName() + "' has failed due to: " + e.getMessage(), e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    public <R> List<R> findByNamedQuery(String queryName, Class<R> clazz, Map<String, Object> parameters) {
        if (queryName == null || queryName.isEmpty() || clazz == null) {
            return Collections.emptyList();
        }

        EntityManager em = Persistence.getEntityManager();
        try {
            TypedQuery<R> query = em.createNamedQuery(queryName, clazz);
            if (parameters != null && !parameters.isEmpty()) {
                parameters.forEach(query::setParameter);
            }
            return query.getResultList();
        } catch (Exception e) {
            log.error("Could not execute query '" + queryName + "' for entity '" + clazz.getSimpleName() + "' " + (parameters != null ? "with parameters '" + parameters.toString() + "' " : "") + "due to: " + e.getMessage(), e);
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    public List<Long> findOverlappingReservations (
            Long reservationId, LocalDateTime startTime, LocalDateTime endTime, Long courtId
    ) {
        EntityManager em = Persistence.getEntityManager();
        String query = """
            select
                res.id
            from
                Reservation res
            where
                res.deleted = false and res.id != :reservationId  and res.court.id = :courtId and (
                    res.reservationStart between :startTime and :endTime
                    or :startTime between res.reservationStart and res.reservationEnd
                    or :endTime between res.reservationStart and res.reservationEnd
                    or (
                        res.reservationStart between :startTime and :endTime
                        and res.reservationEnd between :startTime and :endTime
                    )
                )
        """;
        try {
            TypedQuery<Long> typedQuery = em.createQuery(query, Long.class);
            typedQuery.setParameter("reservationId", reservationId);
            typedQuery.setParameter("courtId", courtId);
            typedQuery.setParameter("startTime", startTime);
            typedQuery.setParameter("endTime", endTime);
            return typedQuery.getResultList();
        } catch (Exception e) {
            log.error("Could not execute query due to: " + e.getMessage(), e);
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

}
