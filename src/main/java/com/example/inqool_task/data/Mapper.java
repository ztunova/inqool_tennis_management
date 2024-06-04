package com.example.inqool_task.data;

import com.example.inqool_task.data.dto.CourtSurfaceDto;
import com.example.inqool_task.data.dto.CreateSurfaceDto;
import com.example.inqool_task.data.model.CourtSurface;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public CourtSurface mapToEntity(CreateSurfaceDto surfaceDto) {
        CourtSurface surface = new CourtSurface();
        surface.setSurface(surfaceDto.getSurface());
        surface.setPricePerMinute(surfaceDto.getPricePerMinute());
        return surface;
    }

    public CourtSurface mapToEntity(CourtSurfaceDto surfaceDto) {
        CourtSurface surface = new CourtSurface();
        surface.setId(surfaceDto.getId());
        surface.setSurface(surfaceDto.getSurface());
        surface.setPricePerMinute(surfaceDto.getPricePerMinute());
        return surface;
    }

    public CourtSurfaceDto mapToDto(CourtSurface surface) {
        CourtSurfaceDto surfaceDto = new CourtSurfaceDto();
        surfaceDto.setId(surface.getId());
        surfaceDto.setSurface(surface.getSurface());
        surfaceDto.setPricePerMinute(surface.getPricePerMinute());
        return surfaceDto;
    }

}
