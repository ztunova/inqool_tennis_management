package com.example.inqool_task.data;

import com.example.inqool_task.data.dto.CourtDto;
import com.example.inqool_task.data.dto.CourtSurfaceDto;
import com.example.inqool_task.data.dto.CreateCourtDto;
import com.example.inqool_task.data.dto.CreateSurfaceDto;
import com.example.inqool_task.data.dto.CustomerDto;
import com.example.inqool_task.data.dto.ReservationRequestDto;
import com.example.inqool_task.data.dto.ReservationResponseDto;
import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.data.model.CourtSurface;
import com.example.inqool_task.data.model.Customer;
import com.example.inqool_task.data.model.GameType;
import com.example.inqool_task.data.model.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Service
public class Mapper {

    public CourtSurface mapToEntity(CreateSurfaceDto surfaceDto) {
        CourtSurface surface = new CourtSurface();
        surface.setSurface(surfaceDto.getSurface());
        surface.setPricePerMinute(surfaceDto.getPricePerMinute());
        return surface;
    }

    public CourtSurface mapToEntity(CourtSurfaceDto surfaceDto) {
        CourtSurface surface = new CourtSurface();
        surface.setId(surfaceDto.getId());
        surface.setSurface(surfaceDto.getSurface());
        surface.setPricePerMinute(surfaceDto.getPricePerMinute());
        return surface;
    }

    public CourtSurfaceDto mapToDto(CourtSurface surface) {
        if (surface == null) {
            return null;
        }

        CourtSurfaceDto surfaceDto = new CourtSurfaceDto();
        surfaceDto.setId(surface.getId());
        surfaceDto.setSurface(surface.getSurface());
        surfaceDto.setPricePerMinute(surface.getPricePerMinute());
        return surfaceDto;
    }

//    -------------- courts

    public Court mapToEntity(CreateCourtDto courtDto) {
        Court court = new Court();
        court.setCourtNumber(courtDto.getCourtNumber());
        return court;
    }

    public Court mapToEntity(CourtDto courtDto) {
        Court court = new Court();
        court.setId(courtDto.getId());
        court.setCourtNumber(courtDto.getCourtNumber());
        court.setSurface(mapToEntity(courtDto.getSurface()));
        return court;
    }

    public CourtDto mapToDto(Court court) {
        CourtDto courtDto = new CourtDto();
        courtDto.setId(court.getId());
        courtDto.setCourtNumber(court.getCourtNumber());
        courtDto.setSurface(mapToDto(court.getSurface()));
        return courtDto;
    }

//    -------------- reservations

    public Reservation mapToEntity(ReservationRequestDto reservationRequestDto) {
        Reservation reservation = new Reservation();
        try {
            reservation.setReservationStart(LocalDateTime.parse(reservationRequestDto.getReservationStart()));
            reservation.setReservationEnd(LocalDateTime.parse(reservationRequestDto.getReservationEnd()));
        }
        catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }

        reservation.setGameType(GameType.valueOf(reservationRequestDto.getGameType()));
        reservation.setCustomer(mapToEntity(reservationRequestDto.getCustomer()));
        // court

        return reservation;
    }

    public ReservationResponseDto mapToDto(Reservation reservation) {
        ReservationResponseDto reservationDto = new ReservationResponseDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setGameType(reservation.getGameType());
        reservationDto.setReservationStart(reservation.getReservationStart().toString());
        reservationDto.setReservationEnd(reservation.getReservationEnd().toString());
        reservationDto.setTotalPrice(reservation.getTotalPrice());
        if (reservation.getCourt() != null) {
            reservationDto.setCourt(mapToDto(reservation.getCourt()));
        }
        reservationDto.setCustomer(mapToDto(reservation.getCustomer()));

        return reservationDto;
    }

//    -------------- customers

    public Customer mapToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        return customer;
    }

    public CustomerDto mapToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        return customerDto;
    }

}
