package com.example.inqool_task.controller;

import com.example.inqool_task.data.dto.ReservationRequestDto;
import com.example.inqool_task.data.dto.ReservationResponseDto;
import com.example.inqool_task.facade.ReservationFacade;
import com.example.inqool_task.util.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    @Mock
    ReservationFacade reservationFacade;

    @InjectMocks
    ReservationController reservationController;

    @Test
    void createReservation_creationSuccessful_returnsResponseEntityCreated() {
        ReservationRequestDto reservationRequestDto = TestDataFactory.reservationRequestDto;
        ReservationResponseDto reservationResponseDto = TestDataFactory.reservationResponseDto;
        Mockito.when(reservationFacade.create(reservationRequestDto)).thenReturn(reservationResponseDto);

        ResponseEntity<ReservationResponseDto> reservationDtoResponseEntity =
                reservationController.createReservation(reservationRequestDto);

        assertThat(reservationDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(reservationDtoResponseEntity.getBody()).isEqualTo(reservationResponseDto);
    }

    @Test
    void findById_reservationFound_returnsResponseEntityOk() {
        ReservationResponseDto reservationResponseDto = TestDataFactory.reservationResponseDto;
        Mockito.when(reservationFacade.getById(1L)).thenReturn(reservationResponseDto);

        ResponseEntity<ReservationResponseDto> reservationDtoResponseEntity =
                reservationController.getReservationById(1L);

        assertThat(reservationDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reservationDtoResponseEntity.getBody()).isEqualTo(reservationResponseDto);
    }

    @Test
    void getAllReservations_someReservationsListed_returnsResponseEntityOk() {
        ReservationResponseDto reservationResponseDto1 = TestDataFactory.reservationResponseDto;
        ReservationResponseDto reservationResponseDto2 = TestDataFactory.reservationResponseDto;
        List<ReservationResponseDto> reservationResponseDtoList =
                List.of(reservationResponseDto1, reservationResponseDto2);

        Mockito.when(reservationFacade.getAll()).thenReturn(reservationResponseDtoList);

        ResponseEntity<List<ReservationResponseDto>> reservationDtoResponseEntity =
                reservationController.getAllReservations();

        assertThat(reservationDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reservationDtoResponseEntity.getBody()).isEqualTo(reservationResponseDtoList);
    }

    @Test
    void getCourtsReservations_someReservationsListed_returnsResponseEntityOk() {
        ReservationResponseDto reservationResponseDto1 = TestDataFactory.reservationResponseDto;
        ReservationResponseDto reservationResponseDto2 = TestDataFactory.reservationResponseDto;
        List<ReservationResponseDto> reservationResponseDtoList =
                List.of(reservationResponseDto1, reservationResponseDto2);

        Mockito.when(reservationFacade.getByCourtNumber(1L)).thenReturn(reservationResponseDtoList);

        ResponseEntity<List<ReservationResponseDto>> reservationDtoResponseEntity =
                reservationController.getReservationsByCourtNumber(1L);

        assertThat(reservationDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reservationDtoResponseEntity.getBody()).isEqualTo(reservationResponseDtoList);
    }

    @Test
    void getCustomersReservations_someReservationsListed_returnsResponseEntityOk() {
        ReservationResponseDto reservationResponseDto1 = TestDataFactory.reservationResponseDto;
        ReservationResponseDto reservationResponseDto2 = TestDataFactory.reservationResponseDto;
        List<ReservationResponseDto> reservationResponseDtoList =
                List.of(reservationResponseDto1, reservationResponseDto2);

        Mockito.when(reservationFacade.getByCustomerPhoneNumber(TestDataFactory.PHONE_NUMBER, false))
                .thenReturn(reservationResponseDtoList);

        ResponseEntity<List<ReservationResponseDto>> reservationDtoResponseEntity =
                reservationController.getCustomersReservations(TestDataFactory.PHONE_NUMBER, false);

        assertThat(reservationDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reservationDtoResponseEntity.getBody()).isEqualTo(reservationResponseDtoList);
    }

    @Test
    void updateReservation_updateSuccessful_returnsResponseEntityOk() {
        ReservationRequestDto reservationRequestDto = TestDataFactory.reservationRequestDto;
        ReservationResponseDto reservationResponseDto = TestDataFactory.reservationResponseDto;
        Mockito.when(reservationFacade.update(1L, reservationRequestDto)).thenReturn(reservationResponseDto);

        ResponseEntity<ReservationResponseDto> reservationDtoResponseEntity =
                reservationController.updateReservation(1L, reservationRequestDto);

        assertThat(reservationDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reservationDtoResponseEntity.getBody()).isEqualTo(reservationResponseDto);
    }

    @Test
    void deleteReservation_deletionSuccessful_returnsResponseNoContent() {
        Mockito.doNothing().when(reservationFacade).delete(1L);

        ResponseEntity<Void> reservationDtoResponseEntity = reservationController.deleteReservation(1L);

        assertThat(reservationDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
