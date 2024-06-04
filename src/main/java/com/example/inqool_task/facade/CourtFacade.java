package com.example.inqool_task.facade;

import com.example.inqool_task.data.Mapper;
import com.example.inqool_task.data.dto.CourtDto;
import com.example.inqool_task.data.dto.CourtSurfaceDto;
import com.example.inqool_task.data.dto.CreateCourtDto;
import com.example.inqool_task.data.dto.CreateSurfaceDto;
import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.data.model.CourtSurface;
import com.example.inqool_task.service.CourtService;
import com.example.inqool_task.service.CourtSurfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtFacade {

    private final CourtService courtService;

    private final Mapper mapper;

    @Autowired
    public CourtFacade(CourtService service, Mapper mapper) {
        this.courtService = service;
        this.mapper = mapper;
    }

    public CourtDto create(CreateCourtDto newCourt) {
        return mapper.mapToDto(courtService.create(mapper.mapToEntity(newCourt), newCourt.getSurfaceId()));
    }

}
