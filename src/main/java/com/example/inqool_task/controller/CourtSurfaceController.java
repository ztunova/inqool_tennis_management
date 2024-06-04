package com.example.inqool_task.controller;

import com.example.inqool_task.data.dto.CourtSurfaceDto;
import com.example.inqool_task.data.dto.CreateSurfaceDto;
import com.example.inqool_task.facade.CourtSurfaceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<CourtSurfaceDto> createSurface(@RequestBody CreateSurfaceDto surface) {
        return new ResponseEntity<>(surfaceFacade.create(surface), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtSurfaceDto> getSurfaceById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(surfaceFacade.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CourtSurfaceDto>> getAllSurfaces() {
        return new ResponseEntity<>(surfaceFacade.getAll(), HttpStatus.OK);
    }

}
