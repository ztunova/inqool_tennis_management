package com.example.inqool_task.service;

import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.data.model.Customer;
import com.example.inqool_task.data.model.Reservation;
import com.example.inqool_task.exceptions.EntityNotFoundException;
import com.example.inqool_task.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final CrudRepository crudRepository;

    private final CustomerService customerService;

    private final CourtService courtService;

    @Autowired
    public ReservationService(CrudRepository crudRepository, CustomerService customerService, CourtService courtService) {
        this.crudRepository = crudRepository;
        this.customerService = customerService;
        this.courtService = courtService;
    }

    public Reservation create(Reservation reservation, Long courtId) {
        checkOverlappingReservations(courtId, reservation.getReservationStart(), reservation.getReservationEnd());

        Customer customer = customerService.findCustomer(reservation.getCustomer());
        Court court = courtService.getById(courtId);

        reservation.setCustomer(customer);
        customer.addReservation(reservation);
        reservation.setCourt(court);
        court.addReservation(reservation);
        reservation.calculateTotalPrice();

        Reservation createdReservation = crudRepository.create(reservation);
        if (createdReservation == null) {
            System.out.println("FAIL CREATE RESERVATION");
        }

//        createdReservation.calculateTotalPrice();
        return createdReservation;
    }

    public Reservation getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Provided id must not be null");
        }

        List<Reservation> reservations = crudRepository.findByNamedQuery(
                Reservation.FIND_BY_ID_QUERY, Reservation.class,
                Collections.singletonMap("id", id)
        );
        if (reservations.isEmpty()) {
            throw new EntityNotFoundException("Reservation", id);
        }

        Reservation result = reservations.get(0);
 //       result.calculateTotalPrice();
        return result;
    }

    public List<Reservation> getAll() {
        return crudRepository.findByNamedQuery(Reservation.FIND_ALL_QUERY, Reservation.class, null);
    }

    public List<Reservation> getByCourtNumber(Long courtNumber) {
        Optional<Court> courtOpt = courtService.getCourtByNumber(courtNumber);
        if (courtOpt.isEmpty()) {
            throw new EntityNotFoundException("Court with number " + courtNumber + " not found");
        }

        Set<Reservation> courtsReservations = courtOpt.get().getReservations();

        // order reservations from newest to oldest by created at field
        Comparator<Reservation> compareByDateCreated = new Comparator<Reservation>() {
            @Override
            public int compare(Reservation o1, Reservation o2) {
                return o2.getCreatedAt().compareTo(o1.getCreatedAt());
            }
        };
        TreeSet<Reservation> orderedReservations = new TreeSet<>(compareByDateCreated);
        orderedReservations.addAll(courtsReservations);
        return orderedReservations.stream().toList();
    }

    public List<Reservation> getByCustomerPhoneNumber(String phoneNumber, boolean onlyFuture) {
        Optional<Customer> customerOpt = customerService.getCustomerByPhoneNumber(phoneNumber);
        if (customerOpt.isEmpty()) {
            throw new EntityNotFoundException("Customer with phone number " + phoneNumber + " not found");
        }

        Set<Reservation> customersReservations = customerOpt.get().getReservations();
        List<Reservation> result = new ArrayList<>();
        if (onlyFuture) {
            result = customersReservations.stream()
                    .filter(r -> r.getReservationStart().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList());
        }
        else {
            result = customersReservations.stream().toList();
        }
        return result;
    }



//    public void delete(Long id) {}

    private void checkOverlappingReservations(Long courtId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Long> overlappingReservationIds = crudRepository.findOverlappingReservations(courtId, startTime, endTime);
        if (!overlappingReservationIds.isEmpty()) {
            throw new IllegalArgumentException("Reservation overlapping with folowing reservations: " + overlappingReservationIds);
        }
    }

}
