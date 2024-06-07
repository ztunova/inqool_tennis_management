package com.example.inqool_task.service;

import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.data.model.Customer;
import com.example.inqool_task.data.model.Reservation;
import com.example.inqool_task.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
