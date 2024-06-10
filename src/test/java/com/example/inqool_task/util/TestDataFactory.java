package com.example.inqool_task.util;

import com.example.inqool_task.data.dto.CourtRequestDto;
import com.example.inqool_task.data.dto.CourtResponseDto;
import com.example.inqool_task.data.dto.CourtSurfaceRequestDto;
import com.example.inqool_task.data.dto.CourtSurfaceResponseDto;
import com.example.inqool_task.data.dto.CustomerDto;
import com.example.inqool_task.data.dto.ReservationRequestDto;
import com.example.inqool_task.data.dto.ReservationResponseDto;
import com.example.inqool_task.data.model.GameType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TestDataFactory {

    private static final Long ID = 1L;
    private static final GameType GAME_TYPE = GameType.SINGLES_MATCH;
    private static final LocalDateTime RESERVATION_START = LocalDateTime.now();
    private static final LocalDateTime RESERVATION_END = LocalDateTime.now().plusHours(1);

    public static final String PHONE_NUMBER = "0915432567";

    public static CourtSurfaceRequestDto courtSurfaceRequestDto = getSurfaceRequestDto();
    public static CourtSurfaceResponseDto courtSurfaceResponseDto = getSurfaceResponseDto();
    public static CourtRequestDto courtRequestDto = getCourtRequestDto();
    public static CourtResponseDto courtResponseDto = getCourtResponseDto();
    public static ReservationRequestDto reservationRequestDto = getReservationRequestDto();
    public static ReservationResponseDto reservationResponseDto = getReservationResponseDto();

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

    private static CustomerDto getCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Percy Jackson");
        customerDto.setPhoneNumber(PHONE_NUMBER);
        return customerDto;
    }
}
