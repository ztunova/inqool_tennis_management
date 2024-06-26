package com.example.inqool_task.util;

import com.example.inqool_task.data.dto.CourtRequestDto;
import com.example.inqool_task.data.dto.CourtResponseDto;
import com.example.inqool_task.data.dto.CourtSurfaceRequestDto;
import com.example.inqool_task.data.dto.CourtSurfaceResponseDto;
import com.example.inqool_task.data.dto.CustomerDto;
import com.example.inqool_task.data.dto.ReservationRequestDto;
import com.example.inqool_task.data.dto.ReservationResponseDto;
import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.data.model.CourtSurface;
import com.example.inqool_task.data.model.Customer;
import com.example.inqool_task.data.model.GameType;
import com.example.inqool_task.data.model.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;

@Component
public class TestDataFactory {

    private static final Long ID = 1L;
    private static final GameType GAME_TYPE = GameType.SINGLES_MATCH;
    public static final LocalDateTime RESERVATION_START = LocalDateTime.now().plusDays(1);
    public static final LocalDateTime RESERVATION_END = LocalDateTime.now().plusDays(1).plusHours(1);

    public static final String PHONE_NUMBER = "0915432567";

    public static final String CUSTOMER_NAME = "Percy Jackson";

    public static CourtSurfaceRequestDto courtSurfaceRequestDto = getSurfaceRequestDto();
    public static CourtSurfaceResponseDto courtSurfaceResponseDto = getSurfaceResponseDto();
    public static final CourtSurface courtSurfaceEntity = getSurfaceEntity();
    public static CourtRequestDto courtRequestDto = getCourtRequestDto();
    public static final Court courtEntity = getCourtEntity();
    public static CourtResponseDto courtResponseDto = getCourtResponseDto();
    public static ReservationRequestDto reservationRequestDto = getReservationRequestDto();
    public static ReservationResponseDto reservationResponseDto = getReservationResponseDto();
    public static Reservation reservationEntity = getReservationEntity();
    public static Customer customerEntity = getCustomerEntity();
    public static CustomerDto customerDto = getCustomerDto();

    private static CourtSurfaceRequestDto getSurfaceRequestDto() {
        CourtSurfaceRequestDto surfaceRequestDto = new CourtSurfaceRequestDto();
        surfaceRequestDto.setSurface("grass");
        surfaceRequestDto.setPricePerMinute(1.0);
        return surfaceRequestDto;
    }

    private static CourtSurfaceResponseDto getSurfaceResponseDto() {
        CourtSurfaceResponseDto surfaceResponseDto = new CourtSurfaceResponseDto();
        surfaceResponseDto.setId(ID);
        surfaceResponseDto.setSurface("grass");
        surfaceResponseDto.setPricePerMinute(1.0);
        return surfaceResponseDto;
    }

    private static CourtSurface getSurfaceEntity() {
        CourtSurface surface = new CourtSurface();
        surface.setId(ID);
        surface.setSurface("grass");
        surface.setPricePerMinute(1.0);
        surface.setCourts(new HashSet<>());
        return surface;
    }

    private static CourtRequestDto getCourtRequestDto() {
        CourtRequestDto courtRequestDto = new CourtRequestDto();
        courtRequestDto.setCourtNumber(1L);
        courtRequestDto.setSurfaceId(ID);
        return courtRequestDto;
    }

    private static CourtResponseDto getCourtResponseDto() {
        CourtResponseDto courtResponseDto = new CourtResponseDto();
        courtResponseDto.setId(ID);
        courtResponseDto.setCourtNumber(1L);
        courtResponseDto.setSurface(courtSurfaceResponseDto);
        return courtResponseDto;
    }

    private static Court getCourtEntity() {
        Court court = new Court();
        court.setId(ID);
        court.setCourtNumber(1L);
        court.setSurface(getSurfaceEntity());
        court.setReservations(new HashSet<>());
        return court;
    }

    private static ReservationRequestDto getReservationRequestDto() {
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto();
        reservationRequestDto.setCourtId(ID);
        reservationRequestDto.setGameType(GAME_TYPE.toString());
        reservationRequestDto.setReservationStart(RESERVATION_START.toString());
        reservationRequestDto.setReservationEnd(RESERVATION_END.toString());
        reservationRequestDto.setCustomer(getCustomerDto());
        return reservationRequestDto;
    }

    private static ReservationResponseDto getReservationResponseDto() {
        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();
        reservationResponseDto.setId(ID);
        reservationResponseDto.setGameType(GAME_TYPE);
        reservationResponseDto.setReservationStart(RESERVATION_START.toString());
        reservationResponseDto.setReservationEnd(RESERVATION_END.toString());
        reservationResponseDto.setTotalPrice(100);
        reservationResponseDto.setCourt(getCourtResponseDto());
        reservationResponseDto.setCustomer(getCustomerDto());
        return reservationResponseDto;
    }

    private static Reservation getReservationEntity() {
        Reservation reservation = new Reservation();
        reservation.setId(ID);
        reservation.setReservationStart(RESERVATION_START);
        reservation.setReservationEnd(RESERVATION_END);
        reservation.setGameType(GAME_TYPE);
        reservation.setCustomer(getCustomerEntity());
        reservation.setCourt(getCourtEntity());
        reservation.setTotalPrice(100);
        reservation.setCreatedAt(LocalDateTime.now());
        return reservation;
    }

    private static CustomerDto getCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Percy Jackson");
        customerDto.setPhoneNumber(PHONE_NUMBER);
        return customerDto;
    }

    private static Customer getCustomerEntity() {
        Customer customer = new Customer();
        customer.setName(CUSTOMER_NAME);
        customer.setPhoneNumber(PHONE_NUMBER);
        return customer;
    }
}
