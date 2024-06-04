package com.example.inqool_task.service;

import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.data.model.CourtSurface;
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
//        surface.addCourt(court);
        Court createdCourt = crudRepository.create(court);

        if (createdCourt == null) {
            System.out.println("FAIL CREATE SURFACE");
        }

        return createdCourt;
    }

}
