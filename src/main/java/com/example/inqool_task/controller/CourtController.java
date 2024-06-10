package com.example.inqool_task.controller;

import com.example.inqool_task.data.dto.CourtResponseDto;
import com.example.inqool_task.data.dto.CourtRequestDto;
import com.example.inqool_task.facade.CourtFacade;
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
@RequestMapping(path = "/court", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourtController {

    private final CourtFacade courtFacade;

    @Autowired
    public CourtController(CourtFacade facade) {
        this.courtFacade = facade;
    }

    @PostMapping
    public ResponseEntity<CourtResponseDto> createCourt(@RequestBody CourtRequestDto court) {
        return new ResponseEntity<>(courtFacade.create(court), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtResponseDto> getCourtById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(courtFacade.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CourtResponseDto>> getAllCourts() {
        return new ResponseEntity<>(courtFacade.getAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourtResponseDto> updateCourt(
            @PathVariable("id") Long id, @RequestBody CourtRequestDto court) {
        return new ResponseEntity<>(courtFacade.update(id, court), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourt(@PathVariable("id") Long id) {
        courtFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
