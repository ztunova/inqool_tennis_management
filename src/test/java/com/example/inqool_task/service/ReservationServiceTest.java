package com.example.inqool_task.service;

import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.data.model.Customer;
import com.example.inqool_task.data.model.Reservation;
import com.example.inqool_task.exceptions.EntityNotFoundException;
import com.example.inqool_task.repository.CrudRepository;
import com.example.inqool_task.util.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private CrudRepository crudRepository;

    @Mock
    private CourtService courtService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private ReservationService reservationService;

//    @Test
//    void createReservation_creationSuccessful_returnsReservation() {
//        Reservation reservation = TestDataFactory.reservationEntity;
//
//        Mockito.when(crudRepository.findOverlappingReservations(
//                -1L, TestDataFactory.RESERVATION_START, TestDataFactory.RESERVATION_END, 1L))
//                .thenReturn(new ArrayList<>());
//        Mockito.when(customerService.findCustomer(TestDataFactory.customerEntity)).thenReturn(TestDataFactory.customerEntity);
//        Mockito.when(courtService.getById(1L)).thenReturn(TestDataFactory.courtEntity);
//        Mockito.when(crudRepository.create(reservation)).thenReturn(reservation);
//
//        Reservation reservationReturned = reservationService.create(reservation, 1L);
//
//        assertThat(reservationReturned).isEqualTo(reservation);
//    }

//    @Test
//    void createReservation_reservationOverlapping_throwsException() {
//        Reservation reservation = TestDataFactory.reservationEntity;
//
//        Mockito.lenient().when(crudRepository.findOverlappingReservations(
//                        -1L, TestDataFactory.RESERVATION_START, TestDataFactory.RESERVATION_END, 1L))
//                .thenReturn(List.of(1L, 2L));
//
//        assertThrows(IllegalArgumentException.class, () -> reservationService.create(reservation, 1L));
//    }

    @Test
    void findById_reservationFound_returnsReservation() {
        Reservation reservation = TestDataFactory.reservationEntity;
        Map<String, Object> params = Collections.singletonMap("id", reservation.getId());
        List<Reservation> reservations = List.of(reservation);

        Mockito.when(crudRepository.findByNamedQuery(Reservation.FIND_BY_ID_QUERY, Reservation.class, params)).thenReturn(reservations);

        Reservation reservationReturned = reservationService.getById(1L);

        assertThat(reservationReturned).isEqualTo(reservation);
    }

    @Test
    void findById_nullId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> reservationService.getById(null));
    }

    @Test
    void findById_reservationNotFound_throwsException() {
        Reservation reservation = TestDataFactory.reservationEntity;
        Map<String, Object> params = Collections.singletonMap("id", reservation.getId());

        Mockito.when(crudRepository.findByNamedQuery(Reservation.FIND_BY_ID_QUERY, Reservation.class, params))
                .thenReturn(new ArrayList<>());

        assertThrows(EntityNotFoundException.class, () -> reservationService.getById(1L));
    }

    @Test
    void getAll_someReservationsListed_returnsListOfReservations() {
        Reservation reservation = TestDataFactory.reservationEntity;
        List<Reservation> reservations = List.of(reservation);

        Mockito.when(crudRepository.findByNamedQuery(Reservation.FIND_ALL_QUERY, Reservation.class, null))
                .thenReturn(reservations);

        List<Reservation> reservationsReturned = reservationService.getAll();

        assertThat(reservationsReturned).isEqualTo(reservations);
    }

    @Test
    void getByCourtNumber_someReservationsListed_returnsListOfReservations() {
        Reservation reservation = TestDataFactory.reservationEntity;
        List<Reservation> reservations = List.of(reservation);
        Court court = TestDataFactory.courtEntity;
        court.addReservation(reservation);

        Mockito.when(courtService.getCourtByNumber(1L)).thenReturn(Optional.of(court));

        List<Reservation> reservationsReturned = reservationService.getByCourtNumber(1L);

        assertThat(reservationsReturned).isEqualTo(reservations);
    }

    @Test
    void getByCourtNumber_courtNotFound_throwsException() {
        Mockito.when(courtService.getCourtByNumber(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> reservationService.getByCourtNumber(1L));
    }

//    @Test
//    void getByCustomerPhoneNumber_allReservationsListed_returnsListOfReservations() {
//        Reservation pastReservation = TestDataFactory.reservationEntity;
//        pastReservation.setReservationStart(LocalDateTime.now().minusDays(1));
//        pastReservation.setReservationEnd(LocalDateTime.now().minusDays(1).plusHours(1));
//
//        List<Reservation> reservations = List.of(pastReservation);
//        Customer customer = TestDataFactory.customerEntity;
//        customer.addReservation(pastReservation);
//
//        Mockito.when(customerService.getCustomerByPhoneNumber(TestDataFactory.PHONE_NUMBER)).thenReturn(Optional.of(customer));
//
//        List<Reservation> reservationsReturned = reservationService.getByCustomerPhoneNumber(TestDataFactory.PHONE_NUMBER, false);
//
//        assertThat(reservationsReturned).isEqualTo(reservations);
//    }

    @Test
    void getByCustomerPhoneNumber_futureReservationsListed_returnsListOfReservations() {
        Reservation pastReservation = TestDataFactory.reservationEntity;
        pastReservation.setReservationStart(LocalDateTime.now().minusDays(1));
        pastReservation.setReservationEnd(LocalDateTime.now().minusDays(1).plusHours(1));

        List<Reservation> reservations = List.of(pastReservation);
        Customer customer = TestDataFactory.customerEntity;
        customer.addReservation(pastReservation);

        Mockito.when(customerService.getCustomerByPhoneNumber(TestDataFactory.PHONE_NUMBER)).thenReturn(Optional.of(customer));

        List<Reservation> reservationsReturned = reservationService.getByCustomerPhoneNumber(TestDataFactory.PHONE_NUMBER, true);

        assertThat(reservationsReturned).isEmpty();
    }

//    @Test
//    void updateReservation_reservationFound_returnsReservation() {
//        Reservation reservation = TestDataFactory.reservationEntity;
//        Map<String, Object> params = Collections.singletonMap("id", reservation.getId());
//        List<Reservation> reservations = List.of(reservation);
//
//        Mockito.when(crudRepository.findByNamedQuery(Reservation.FIND_BY_ID_QUERY, Reservation.class, params))
//                .thenReturn(reservations);
//        Mockito.when(customerService.findCustomer(TestDataFactory.customerEntity)).thenReturn(TestDataFactory.customerEntity);
//        Mockito.when(courtService.getById(1L)).thenReturn(TestDataFactory.courtEntity);
//        Mockito.when(crudRepository.update(reservation)).thenReturn(reservation);
//
//        Reservation reservationReturned = reservationService.update(1L, reservation);
//
//        assertThat(reservationReturned).isEqualTo(reservation);
//    }

    @Test
    void deleteReservation_reservationFound_returnsVoid() {
        Reservation reservation = TestDataFactory.reservationEntity;
        Map<String, Object> params = Collections.singletonMap("id", reservation.getId());
        List<Reservation> reservations = List.of(reservation);

        Mockito.when(crudRepository.findByNamedQuery(Reservation.FIND_BY_ID_QUERY, Reservation.class, params))
                .thenReturn(reservations);
        Mockito.when(crudRepository.update(reservation)).thenReturn(reservation);

        reservationService.delete(1L);

        verify(crudRepository, times(1)).findByNamedQuery(
                Reservation.FIND_BY_ID_QUERY, Reservation.class, params);
        verify(crudRepository, times(1)).update(reservation);
    }

}
