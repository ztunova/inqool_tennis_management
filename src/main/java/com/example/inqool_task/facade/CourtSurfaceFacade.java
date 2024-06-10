package com.example.inqool_task.facade;

import com.example.inqool_task.data.Mapper;
import com.example.inqool_task.data.dto.CourtSurfaceResponseDto;
import com.example.inqool_task.data.dto.CourtSurfaceRequestDto;
import com.example.inqool_task.data.model.CourtSurface;
import com.example.inqool_task.service.CourtSurfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtSurfaceFacade {
    private final CourtSurfaceService surfaceService;

    private final Mapper mapper;

    @Autowired
    public CourtSurfaceFacade(CourtSurfaceService service, Mapper mapper) {
        this.surfaceService = service;
        this.mapper = mapper;
    }

    public CourtSurfaceResponseDto create(CourtSurfaceRequestDto newSurface) {
        return mapper.mapToDto(surfaceService.create(mapper.mapToEntity(newSurface)));
    }

    public CourtSurfaceResponseDto getById(Long id) {
        return mapper.mapToDto(surfaceService.getById(id));
    }

    public List<CourtSurfaceResponseDto> getAll() {
        List<CourtSurface> surfaces = surfaceService.getAll();
        return surfaces.stream().map(mapper::mapToDto).toList();
    }

    public CourtSurfaceResponseDto update(Long id, CourtSurfaceRequestDto surfaceToUpdateDto) {
        CourtSurface surfaceToUpdate = mapper.mapToEntity(surfaceToUpdateDto);
        surfaceToUpdate.setId(id);
        return mapper.mapToDto(surfaceService.update(surfaceToUpdate));
    }

    public void delete(Long id) {
        surfaceService.delete(id);
    }

}
