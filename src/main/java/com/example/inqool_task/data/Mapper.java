package com.example.inqool_task.data;

import com.example.inqool_task.data.dto.CourtResponseDto;
import com.example.inqool_task.data.dto.CourtSurfaceResponseDto;
import com.example.inqool_task.data.dto.CourtRequestDto;
import com.example.inqool_task.data.dto.CourtSurfaceRequestDto;
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

//    -------------- court surface

    public CourtSurface mapToEntity(CourtSurfaceRequestDto surfaceDto) {
        CourtSurface surface = new CourtSurface();
        surface.setSurface(surfaceDto.getSurface());
        surface.setPricePerMinute(surfaceDto.getPricePerMinute());
        return surface;
    }

    public CourtSurfaceResponseDto mapToDto(CourtSurface surface) {
        if (surface == null) {
            return null;
        }

        CourtSurfaceResponseDto surfaceDto = new CourtSurfaceResponseDto();
        surfaceDto.setId(surface.getId());
        surfaceDto.setSurface(surface.getSurface());
        surfaceDto.setPricePerMinute(surface.getPricePerMinute());
        return surfaceDto;
    }

//    -------------- court

    public Court mapToEntity(CourtRequestDto courtDto) {
        Court court = new Court();
        court.setCourtNumber(courtDto.getCourtNumber());
        return court;
    }

    public CourtResponseDto mapToDto(Court court) {
        if (court == null) {
            return null;
        }

        CourtResponseDto courtResponseDto = new CourtResponseDto();
        courtResponseDto.setId(court.getId());
        courtResponseDto.setCourtNumber(court.getCourtNumber());
        courtResponseDto.setSurface(mapToDto(court.getSurface()));
        return courtResponseDto;
    }

//    -------------- reservation

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

        return reservation;
    }

    public ReservationResponseDto mapToDto(Reservation reservation) {
        ReservationResponseDto reservationDto = new ReservationResponseDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setGameType(reservation.getGameType());
        reservationDto.setReservationStart(reservation.getReservationStart().toString());
        reservationDto.setReservationEnd(reservation.getReservationEnd().toString());
        reservationDto.setTotalPrice(reservation.getTotalPrice());
        reservationDto.setCourt(mapToDto(reservation.getCourt()));
        reservationDto.setCustomer(mapToDto(reservation.getCustomer()));

        return reservationDto;
    }

//    -------------- customer

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
