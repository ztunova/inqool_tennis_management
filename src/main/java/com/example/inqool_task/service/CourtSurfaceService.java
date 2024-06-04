package com.example.inqool_task.service;

import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.data.model.CourtSurface;
//import com.example.inqool_task.exceptions.EntityNotFoundException;
import com.example.inqool_task.exceptions.EntityNotFoundException;
import com.example.inqool_task.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourtSurfaceService {
    private final CrudRepository crudRepository;

    @Autowired
    public CourtSurfaceService(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public CourtSurface create(CourtSurface surface) {
        List<CourtSurface> surfaces = crudRepository.findByNamedQuery(
                CourtSurface.FIND_BY_SURFACE, CourtSurface.class,
                Collections.singletonMap("surface", surface.getSurface())
        );
        if (!surfaces.isEmpty()) {
            throw new IllegalArgumentException("Surface already exists");
        }

        CourtSurface createdSurface = crudRepository.create(surface);

        if (createdSurface == null) {
            System.out.println("FAIL CREATE SURFACE");
        }

        return createdSurface;
    }

    public CourtSurface getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Provided id must not be null");
        }
        List<CourtSurface> surfaces = crudRepository.findByNamedQuery(
                CourtSurface.FIND_BY_ID_QUERY, CourtSurface.class,
                Collections.singletonMap("id", id)
        );
        if (surfaces.isEmpty()) {
            throw new EntityNotFoundException("Court surface", id);
        }
        return surfaces.get(0);
    }

    public List<CourtSurface> getAll() {
        return crudRepository.findByNamedQuery(CourtSurface.FIND_ALL_QUERY, CourtSurface.class, null);
    }

    public CourtSurface update(CourtSurface surfaceToUpdate) {
        CourtSurface updatedSurface = crudRepository.update(surfaceToUpdate);
        if (updatedSurface == null) {
            System.out.println("ERROR");
        }
        return updatedSurface;
    }

    public void delete(Long id) {
        CourtSurface surfaceToDelete = getById(id);
        Set<Court> updatedCourts = new HashSet<>();
        for (Court court : surfaceToDelete.getCourts()) {
            court.setSurface(null);
            updatedCourts.add(court);
        }
        surfaceToDelete.setCourts(updatedCourts);
        surfaceToDelete.setDeleted(true);
        update(surfaceToDelete);
    }
}
