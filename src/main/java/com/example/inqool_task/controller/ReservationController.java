package com.example.inqool_task.controller;

import com.example.inqool_task.data.dto.ReservationRequestDto;
import com.example.inqool_task.data.dto.ReservationResponseDto;
import com.example.inqool_task.facade.ReservationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {

    private final ReservationFacade reservationFacade;

    @Autowired
    public ReservationController(ReservationFacade facade) {
        this.reservationFacade = facade;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto reservation) {
        return new ResponseEntity<>(reservationFacade.create(reservation), HttpStatus.CREATED);
    }
}
