package com.example.inqool_task.controller;

import com.example.inqool_task.data.dto.CourtSurfaceResponseDto;
import com.example.inqool_task.data.dto.CourtSurfaceRequestDto;
import com.example.inqool_task.facade.CourtSurfaceFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/surface",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CourtSurfaceController {

    private final CourtSurfaceFacade surfaceFacade;

    @Autowired
    public CourtSurfaceController(CourtSurfaceFacade facade) {
        this.surfaceFacade = facade;
    }

    @PostMapping
    public ResponseEntity<CourtSurfaceResponseDto> createSurface(@Valid @RequestBody CourtSurfaceRequestDto surface) {
        return new ResponseEntity<>(surfaceFacade.create(surface), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtSurfaceResponseDto> getSurfaceById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(surfaceFacade.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CourtSurfaceResponseDto>> getAllSurfaces() {
        return new ResponseEntity<>(surfaceFacade.getAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourtSurfaceResponseDto> updateSurface(
            @PathVariable("id") Long id, @Valid @RequestBody CourtSurfaceRequestDto surface) {
        return new ResponseEntity<>(surfaceFacade.update(id, surface), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurface(@PathVariable("id") Long id) {
        surfaceFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
