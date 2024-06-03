package com.example.inqool_task.service;

import com.example.inqool_task.data.model.CourtSurface;
import com.example.inqool_task.repository.CrudRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtSurfaceService {
    private final CrudRepositoryInterface<CourtSurface> surfaceRepository;

    @Autowired
    public CourtSurfaceService(CrudRepositoryInterface<CourtSurface> surfaceRepository) {
        this.surfaceRepository = surfaceRepository;
    }

    public CourtSurface create(CourtSurface surface) {
        CourtSurface createdSurface = surfaceRepository.save(surface);

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
