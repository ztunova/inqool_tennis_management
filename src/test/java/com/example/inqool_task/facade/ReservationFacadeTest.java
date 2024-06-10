package com.example.inqool_task.facade;

import com.example.inqool_task.data.Mapper;
import com.example.inqool_task.data.dto.ReservationRequestDto;
import com.example.inqool_task.data.dto.ReservationResponseDto;
import com.example.inqool_task.data.model.Reservation;
import com.example.inqool_task.service.ReservationService;
import com.example.inqool_task.util.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReservationFacadeTest {

    @Mock
    private ReservationService reservationService;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private ReservationFacade reservationFacade;

    @Test
    void createReservation_creationSuccessful_returnsReservationResponseDto() {
        Reservation reservation = TestDataFactory.reservationEntity;
        ReservationResponseDto reservationResponseDto = TestDataFactory.reservationResponseDto;
        ReservationRequestDto reservationRequestDto = TestDataFactory.reservationRequestDto;

        Mockito.when(reservationService.create(reservation, 1L)).thenReturn(reservation);
        Mockito.when(mapper.mapToDto(reservation)).thenReturn(reservationResponseDto);
        Mockito.when(mapper.mapToEntity(reservationRequestDto)).thenReturn(reservation);

        ReservationResponseDto reservationResponseDtoReturned = reservationFacade.create(reservationRequestDto);

        assertThat(reservationResponseDtoReturned).isEqualTo(reservationResponseDto);
    }


    @Test
    void findById_reservationFound_returnsReservationResponseDto() {
        Reservation reservation = TestDataFactory.reservationEntity;
        ReservationResponseDto reservationResponseDto = TestDataFactory.reservationResponseDto;

        Mockito.when(reservationService.getById(1L)).thenReturn(reservation);
        Mockito.when(mapper.mapToDto(reservation)).thenReturn(reservationResponseDto);

        ReservationResponseDto reservationResponseDtoReturned = reservationFacade.getById(1L);

        assertThat(reservationResponseDtoReturned).isEqualTo(reservationResponseDto);
    }


    @Test
    void getAllReservations_someReservationsListed_returnsListOfReservationResponseDtos() {
        Reservation reservation = TestDataFactory.reservationEntity;
        ReservationResponseDto reservationResponseDto = TestDataFactory.reservationResponseDto;

        List<Reservation> reservations = List.of(reservation);
        List<ReservationResponseDto> reservationResponseDtos = List.of(reservationResponseDto);

        Mockito.when(reservationService.getAll()).thenReturn(reservations);
        Mockito.when(mapper.mapToDto(reservation)).thenReturn(reservationResponseDto);

        List<ReservationResponseDto> courtResponseDtos = reservationFacade.getAll();

        assertThat(courtResponseDtos).isEqualTo(reservationResponseDtos);
    }

    @Test
    void getReservationsByCourt_someReservationsListed_returnsListOfReservationResponseDtos() {
        Reservation reservation = TestDataFactory.reservationEntity;
        ReservationResponseDto reservationResponseDto = TestDataFactory.reservationResponseDto;

        List<Reservation> reservations = List.of(reservation);
        List<ReservationResponseDto> reservationResponseDtos = List.of(reservationResponseDto);

        Mockito.when(reservationService.getByCourtNumber(1L)).thenReturn(reservations);
        Mockito.when(mapper.mapToDto(reservation)).thenReturn(reservationResponseDto);

        List<ReservationResponseDto> courtResponseDtos = reservationFacade.getByCourtNumber(1L);

        assertThat(courtResponseDtos).isEqualTo(reservationResponseDtos);
    }

    @Test
    void getReservationsByCustomer_someReservationsListed_returnsListOfReservationResponseDtos() {
        Reservation reservation = TestDataFactory.reservationEntity;
        ReservationResponseDto reservationResponseDto = TestDataFactory.reservationResponseDto;

        List<Reservation> reservations = List.of(reservation);
        List<ReservationResponseDto> reservationResponseDtos = List.of(reservationResponseDto);

        Mockito.when(reservationService.getByCustomerPhoneNumber(TestDataFactory.PHONE_NUMBER, false))
                .thenReturn(reservations);
        Mockito.when(mapper.mapToDto(reservation)).thenReturn(reservationResponseDto);

        List<ReservationResponseDto> courtResponseDtos =
                reservationFacade.getByCustomerPhoneNumber(TestDataFactory.PHONE_NUMBER, false);

        assertThat(courtResponseDtos).isEqualTo(reservationResponseDtos);
    }

    @Test
    void updateReservation_reservationFound_returnsReservationDto() {
        Reservation reservation = TestDataFactory.reservationEntity;
        ReservationResponseDto reservationResponseDto = TestDataFactory.reservationResponseDto;
        ReservationRequestDto reservationRequestDto = TestDataFactory.reservationRequestDto;

        Mockito.when(reservationService.update(1L, reservation)).thenReturn(reservation);
        Mockito.when(mapper.mapToDto(reservation)).thenReturn(reservationResponseDto);
        Mockito.when(mapper.mapToEntity(reservationRequestDto)).thenReturn(reservation);

        ReservationResponseDto returnedReservationResponseDto = reservationFacade.update(1L, reservationRequestDto);

        assertThat(returnedReservationResponseDto).isEqualTo(reservationResponseDto);
    }


    @Test
    void deleteReservation_reservationFound_returnsVoid() {
        Mockito.doNothing().when(reservationService).delete(1L);

        reservationFacade.delete(1L);

        verify(reservationService, times(1)).delete(1L);
    }


}
