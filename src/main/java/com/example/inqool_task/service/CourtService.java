package com.example.inqool_task.service;

import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.data.model.CourtSurface;
import com.example.inqool_task.exceptions.EntityNotFoundException;
import com.example.inqool_task.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CourtService {

    private final CrudRepository crudRepository;

    private final CourtSurfaceService surfaceService;

    @Autowired
    public CourtService(CrudRepository crudRepository, CourtSurfaceService surfaceService) {
        this.crudRepository = crudRepository;
        this.surfaceService = surfaceService;
    }

    public Court create(Court court, Long surfaceId) {
        List<Court> courts = crudRepository.findByNamedQuery(
                Court.FIND_BY_COURT_NUMBER, Court.class,
                Collections.singletonMap("courtNumber", court.getCourtNumber())
        );
        if (!courts.isEmpty()) {
            throw new IllegalArgumentException("Court with given number already exists");
        }

        CourtSurface surface = surfaceService.getById(surfaceId);
        court.setSurface(surface);
        surface.addCourt(court);
        Court createdCourt = crudRepository.create(court);

        if (createdCourt == null) {
            System.out.println("FAIL CREATE SURFACE");
        }

        return createdCourt;
    }

    public Court getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Provided id must not be null");
        }
        List<Court> courts = crudRepository.findByNamedQuery(
                Court.FIND_BY_ID_QUERY, Court.class,
                Collections.singletonMap("id", id)
        );
        if (courts.isEmpty()) {
            throw new EntityNotFoundException("Court", id);
        }
        return courts.get(0);
    }

    public List<Court> getAll() {
         return crudRepository.findByNamedQuery(Court.FIND_ALL_QUERY, Court.class, null);
    }

    public Court update(Court courtToUpdate) {
        surfaceService.getById(courtToUpdate.getSurface().getId());
        Court updatedCourt = crudRepository.update(courtToUpdate);
        if (updatedCourt == null) {
            System.out.println("ERROR");
        }
        return updatedCourt;
    }

    public void delete(Long id) {
        Court courtToDelete = getById(id);
        if (courtToDelete == null) {
            throw new EntityNotFoundException("Court", id);
        }
        courtToDelete.setDeleted(true);
        update(courtToDelete);
    }

}
