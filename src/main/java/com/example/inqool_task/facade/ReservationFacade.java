package com.example.inqool_task.facade;

import com.example.inqool_task.data.Mapper;
import com.example.inqool_task.data.dto.CourtDto;
import com.example.inqool_task.data.dto.ReservationRequestDto;
import com.example.inqool_task.data.dto.ReservationResponseDto;
import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.data.model.Reservation;
import com.example.inqool_task.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationFacade {

    private final ReservationService reservationService;

    private final Mapper mapper;

    @Autowired
    public ReservationFacade(ReservationService service, Mapper mapper) {
        this.reservationService = service;
        this.mapper = mapper;
    }

    public ReservationResponseDto create(ReservationRequestDto reservation){
        return mapper.mapToDto(reservationService.create(mapper.mapToEntity(reservation), reservation.getCourtId()));
    }

    public ReservationResponseDto getById(Long id) {
        return mapper.mapToDto(reservationService.getById(id));
    }

    public List<ReservationResponseDto> getAll() {
        List<Reservation> reservations = reservationService.getAll();
        return reservations.stream().map(mapper::mapToDto).toList();
    }

    public List<ReservationResponseDto> getByCourtNumber(Long courtNumber) {
        List<Reservation> reservations = reservationService.getByCourtNumber(courtNumber);
        return reservations.stream().map(mapper::mapToDto).toList();
    }

    public List<ReservationResponseDto> getByCustomerPhoneNumber(String phoneNumber, boolean onlyFuture) {
        List<Reservation> reservations = reservationService.getByCustomerPhoneNumber(phoneNumber, onlyFuture);
        return reservations.stream().map(mapper::mapToDto).toList();
    }

}
