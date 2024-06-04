package com.example.inqool_task.facade;

import com.example.inqool_task.data.Mapper;
import com.example.inqool_task.data.dto.CourtSurfaceDto;
import com.example.inqool_task.data.dto.CreateSurfaceDto;
import com.example.inqool_task.service.CourtSurfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourtSurfaceFacade {
    private final CourtSurfaceService surfaceService;

    private final Mapper mapper;

    @Autowired
    public CourtSurfaceFacade(CourtSurfaceService service, Mapper mapper) {
        this.surfaceService = service;
        this.mapper = mapper;
    }

    public CourtSurfaceDto create(CreateSurfaceDto newSurface) {
        return mapper.mapToDto(surfaceService.create(mapper.mapToEntity(newSurface)));
    }

    public CourtSurfaceDto getById(Long id) {
        return mapper.mapToDto(surfaceService.getById(id));
    }

}
