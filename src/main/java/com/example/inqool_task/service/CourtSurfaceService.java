package com.example.inqool_task.service;

import com.example.inqool_task.data.model.CourtSurface;
//import com.example.inqool_task.exceptions.EntityNotFoundException;
import com.example.inqool_task.exceptions.EntityNotFoundException;
import com.example.inqool_task.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
        CourtSurface surface = crudRepository.getById(id, CourtSurface.class);
        if (surface == null) {
            throw new EntityNotFoundException("Court surface", id);
        }
        return surface;
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
        surfaceToDelete.setDeleted(true);
        update(surfaceToDelete);
    }
}
