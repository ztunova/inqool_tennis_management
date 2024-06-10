package com.example.inqool_task.facade;

import com.example.inqool_task.data.Mapper;
import com.example.inqool_task.data.dto.CourtResponseDto;
import com.example.inqool_task.data.dto.CourtRequestDto;
import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.service.CourtService;
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

    public CourtResponseDto create(CourtRequestDto newCourt) {
        return mapper.mapToDto(courtService.create(mapper.mapToEntity(newCourt), newCourt.getSurfaceId()));
    }

    public CourtResponseDto getById(Long id) {
        return mapper.mapToDto(courtService.getById(id));
    }

    public List<CourtResponseDto> getAll() {
        List<Court> courts = courtService.getAll();
        return courts.stream().map(mapper::mapToDto).toList();
    }

    public CourtResponseDto update(Long id, CourtRequestDto courtToUpdateDto) {
        Court courtToUpdate = mapper.mapToEntity(courtToUpdateDto);
        courtToUpdate.setId(id);
        return mapper.mapToDto(courtService.update(courtToUpdateDto.getSurfaceId(), courtToUpdate));
    }

    public void delete(Long id) {
        courtService.delete(id);
    }

}
