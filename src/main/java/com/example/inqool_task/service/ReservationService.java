package com.example.inqool_task.service;

import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.data.model.Customer;
import com.example.inqool_task.data.model.Reservation;
import com.example.inqool_task.exceptions.EntityNotFoundException;
import com.example.inqool_task.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
        // TODO: validovat ze casy sa neprekryvaju s inou rezervaciou
        Customer customer = customerService.findCustomer(reservation.getCustomer());
        Court court = courtService.getById(courtId);

        reservation.setCustomer(customer);
        customer.addReservation(reservation);

        reservation.setCourt(court);
        court.addReservation(reservation);

        Reservation createdReservation = crudRepository.create(reservation);

        if (createdReservation == null) {
            System.out.println("FAIL CREATE RESERVATION");
        }

        createdReservation.calculateTotalPrice();
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
        result.calculateTotalPrice();
        return result;
    }

    public List<Reservation> getAll() {
        return crudRepository.findByNamedQuery(Reservation.FIND_ALL_QUERY, Reservation.class, null);
    }

}
