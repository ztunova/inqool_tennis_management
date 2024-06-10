package com.example.inqool_task.data;

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
import com.example.inqool_task.util.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MapperTest {

        private Mapper mapper;

        @BeforeEach
        void setUp() {
            mapper = new Mapper();
        }

        // Tests for CourtSurface mapping
        @Test
        void mapToEntity_CourtSurface() {
            CourtSurfaceRequestDto surfaceDto = TestDataFactory.courtSurfaceRequestDto;
            CourtSurface surface = mapper.mapToEntity(surfaceDto);

            assertNotNull(surface);
            assertEquals("grass", surface.getSurface());
            assertEquals(1.0, surface.getPricePerMinute());
        }

        @Test
        void mapToDto_CourtSurface() {
            CourtSurface surface = TestDataFactory.courtSurfaceEntity;
            CourtSurfaceResponseDto surfaceDto = mapper.mapToDto(surface);

            assertNotNull(surfaceDto);
            assertEquals(1L, surfaceDto.getId());
            assertEquals("grass", surfaceDto.getSurface());
            assertEquals(1.0, surfaceDto.getPricePerMinute());
        }

        // Tests for Court mapping
        @Test
        void mapToEntity_Court() {
            CourtRequestDto courtDto = TestDataFactory.courtRequestDto;
            Court court = mapper.mapToEntity(courtDto);

            assertNotNull(court);
            assertEquals(1L, court.getCourtNumber());
        }

        @Test
        void mapToDto_Court() {
            Court court = TestDataFactory.courtEntity;
            CourtResponseDto courtDto = mapper.mapToDto(court);

            assertNotNull(courtDto);
            assertEquals(1L, courtDto.getId());
            assertEquals(1L, courtDto.getCourtNumber());
        }

        // Tests for Reservation mapping
        @Test
        void mapToEntity_Reservation() {
            ReservationRequestDto reservationDto = new ReservationRequestDto();
            reservationDto.setReservationStart("2023-06-10T10:00");
            reservationDto.setReservationEnd("2023-06-10T12:00");
            reservationDto.setGameType("SINGLES_MATCH");
            reservationDto.setCustomer(TestDataFactory.customerDto);

            Reservation reservation = mapper.mapToEntity(reservationDto);

            assertNotNull(reservation);
            assertEquals(LocalDateTime.of(2023, 6, 10, 10, 0), reservation.getReservationStart());
            assertEquals(LocalDateTime.of(2023, 6, 10, 12, 0), reservation.getReservationEnd());
            assertEquals(GameType.SINGLES_MATCH, reservation.getGameType());
            assertNotNull(reservation.getCustomer());
        }

        @Test
        void mapToEntity_Reservation_InvalidDate() {
            ReservationRequestDto reservationDto = new ReservationRequestDto();
            reservationDto.setReservationStart("invalid-date");
            reservationDto.setReservationEnd("2023-06-10T12:00");

            assertThrows(IllegalArgumentException.class, () -> mapper.mapToEntity(reservationDto));
        }

        @Test
        void mapToDto_Reservation() {
            Reservation reservation = TestDataFactory.reservationEntity;
            reservation.setReservationStart(LocalDateTime.of(2023, 6, 10, 10, 0));
            reservation.setReservationEnd(LocalDateTime.of(2023, 6, 10, 12, 0));

            ReservationResponseDto reservationDto = mapper.mapToDto(reservation);

            assertNotNull(reservationDto);
            assertEquals(1L, reservationDto.getId());
            assertEquals("2023-06-10T10:00", reservationDto.getReservationStart());
            assertEquals("2023-06-10T12:00", reservationDto.getReservationEnd());
            assertEquals(GameType.SINGLES_MATCH, reservationDto.getGameType());
            assertEquals(100.0, reservationDto.getTotalPrice());
        }

        // Tests for Customer mapping
        @Test
        void mapToEntity_Customer() {
            CustomerDto customerDto = TestDataFactory.customerDto;

            Customer customer = mapper.mapToEntity(customerDto);

            assertNotNull(customer);
            assertEquals(TestDataFactory.CUSTOMER_NAME, customer.getName());
            assertEquals(TestDataFactory.PHONE_NUMBER, customer.getPhoneNumber());
        }

        @Test
        void mapToDto_Customer() {
            Customer customer = TestDataFactory.customerEntity;

            CustomerDto customerDto = mapper.mapToDto(customer);

            assertNotNull(customerDto);
            assertEquals(TestDataFactory.CUSTOMER_NAME, customerDto.getName());
            assertEquals(TestDataFactory.PHONE_NUMBER, customerDto.getPhoneNumber());
        }
}
