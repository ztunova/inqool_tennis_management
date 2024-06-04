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

    public CourtDto getById(Long id) {
        return mapper.mapToDto(courtService.getById(id));
    }

    public List<CourtDto> getAll() {
        List<Court> courts = courtService.getAll();
        return courts.stream().map(mapper::mapToDto).toList();
    }

    public CourtDto update(CourtDto courtToUpdateDto) {
        return mapper.mapToDto(courtService.update(mapper.mapToEntity(courtToUpdateDto)));
    }

//    public void delete(Long id) {
//        courtService.delete(id);
//    }

}
