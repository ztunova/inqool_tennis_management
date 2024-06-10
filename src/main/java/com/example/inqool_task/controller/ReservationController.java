package com.example.inqool_task.controller;

import com.example.inqool_task.data.dto.ReservationRequestDto;
import com.example.inqool_task.data.dto.ReservationResponseDto;
import com.example.inqool_task.facade.ReservationFacade;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(reservationFacade.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
        return new ResponseEntity<>(reservationFacade.getAll(), HttpStatus.OK);
    }

    @GetMapping("/court-number/{courtNumber}")
    public ResponseEntity<List<ReservationResponseDto>> getReservationsByCourtNumber(@PathVariable("courtNumber")
                                                                                         Long courtNumber) {
        return new ResponseEntity<>(reservationFacade.getByCourtNumber(courtNumber), HttpStatus.OK);
    }

    @GetMapping("/customers-reservations/{phoneNumber}")
    public ResponseEntity<List<ReservationResponseDto>> getCustomersReservations(
            @PathVariable("phoneNumber") String phoneNumber, @RequestParam (name = "onlyFuture") boolean onlyFuture) {
        return new ResponseEntity<>(reservationFacade.getByCustomerPhoneNumber(phoneNumber, onlyFuture) , HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> updateReservation(
            @PathVariable("id") Long id, @RequestBody ReservationRequestDto reservationDto) {
        return new ResponseEntity<>(reservationFacade.update(id, reservationDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservationFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
