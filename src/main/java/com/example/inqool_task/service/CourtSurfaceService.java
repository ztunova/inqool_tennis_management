package com.example.inqool_task.service;

import com.example.inqool_task.data.model.CourtSurface;
import com.example.inqool_task.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtSurfaceService {
    private final CrudRepository surfaceRepository;

    @Autowired
    public CourtSurfaceService(CrudRepository surfaceRepository) {
        this.surfaceRepository = surfaceRepository;
    }

    public CourtSurface create(CourtSurface surface) {
        CourtSurface createdSurface = surfaceRepository.create(surface);

        if (createdSurface == null) {
            System.out.println("FAIL CREATE SURFACE");
        }

        return createdSurface;
    }

    public CourtSurface getById(Long id) {
        return null;
    }

    public List<CourtSurface> getAll() {
        return null;
    }

    public CourtSurface update(CourtSurface updatedSurface) {
        return null;
    }

    public void delete(Long id) {
        return;
    }
}
