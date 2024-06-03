package com.example.inqool_task.repository;

import com.example.inqool_task.data.model.CourtSurface;
import org.springframework.stereotype.Repository;

@Repository
public class CourtSurfaceRepository extends CrudRepository {


//    @Override
//    public Optional<CourtSurface> get(Long id) {
//        return Optional.empty();
//    }
//
//
//
//    @Override
//    public CourtSurface save(CourtSurface courtSurface) {
//        EntityManager em = Persistence.getEntityManager();
//
//        try {
//            em.getTransaction().begin();
//            em.persist(courtSurface);
//            em.getTransaction().commit();
//            return courtSurface;
//        } catch (Exception e) {
//            System.out.println("Persist operation of entity has failed due to: " + e.getMessage());
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            return null;
//        } finally {
//            em.close();
//        }
//    }

//    @Override
//    public CourtSurface update(CourtSurface courtSurface) {
//        return null;
//    }
//
//    @Override
//    public void delete(CourtSurface courtSurface) {
//
//    }
}
