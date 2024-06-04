package com.example.inqool_task.service;

import com.example.inqool_task.data.model.CourtSurface;
import com.example.inqool_task.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CourtSurfaceService {
    private final CrudRepository surfaceRepository;

    @Autowired
    public CourtSurfaceService(CrudRepository surfaceRepository) {
        this.surfaceRepository = surfaceRepository;
    }

    public CourtSurface create(CourtSurface surface) {
        List<CourtSurface> surfaces = surfaceRepository.findByNamedQuery(
                CourtSurface.FIND_BY_SURFACE, CourtSurface.class,
                Collections.singletonMap("surface", surface.getSurface())
        );
        if (!surfaces.isEmpty()) {
            throw new IllegalArgumentException("Surface already exists");
        }

        CourtSurface createdSurface = surfaceRepository.create(surface);

        if (createdSurface == null) {
            System.out.println("FAIL CREATE SURFACE");
        }

        return createdSurface;
    }

    public CourtSurface getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Provided id must not be null");
        }
        return surfaceRepository.getById(id, CourtSurface.class);
    }

    public List<CourtSurface> getAll() {
        return surfaceRepository.findByNamedQuery(CourtSurface.FIND_ALL_QUERY, CourtSurface.class, null);
    }

    public CourtSurface update(CourtSurface surfaceToUpdate) {
        CourtSurface updatedSurface = surfaceRepository.update(surfaceToUpdate);
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
