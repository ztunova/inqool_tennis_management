package com.example.inqool_task.facade;

import com.example.inqool_task.data.Mapper;
import com.example.inqool_task.data.dto.ReservationRequestDto;
import com.example.inqool_task.data.dto.ReservationResponseDto;
import com.example.inqool_task.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
