package com.example.inqool_task.service;

import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.data.model.CourtSurface;
import com.example.inqool_task.data.model.Reservation;
import com.example.inqool_task.exceptions.EntityNotFoundException;
import com.example.inqool_task.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourtService {

    private final CrudRepository crudRepository;

    private final CourtSurfaceService surfaceService;

    @Autowired
    public CourtService(CrudRepository crudRepository, CourtSurfaceService surfaceService) {
        this.crudRepository = crudRepository;
        this.surfaceService = surfaceService;
    }

    public Court create(Court court, Long surfaceId) {
        Optional<Court> courtOpt = getCourtByNumber(court.getCourtNumber());
        if (courtOpt.isPresent()) {
            throw new IllegalArgumentException("Court with given number already exists");
        }

        CourtSurface surface = surfaceService.getById(surfaceId);
        court.setSurface(surface);
        surface.addCourt(court);
        Court createdCourt = crudRepository.create(court);

        if (createdCourt == null) {
            System.out.println("FAIL CREATE SURFACE");
        }

        return createdCourt;
    }

    public Optional<Court> getCourtByNumber(Long courtNumber) {
        List<Court> courts = crudRepository.findByNamedQuery(
                Court.FIND_BY_COURT_NUMBER, Court.class,
                Collections.singletonMap("courtNumber", courtNumber)
        );

        Court result = null;
        if (!courts.isEmpty()) {
            result = courts.get(0);
        }

        return Optional.ofNullable(result);
    }

    public Court getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Provided id must not be null");
        }
        List<Court> courts = crudRepository.findByNamedQuery(
                Court.FIND_BY_ID_QUERY, Court.class,
                Collections.singletonMap("id", id)
        );
        if (courts.isEmpty()) {
            throw new EntityNotFoundException("Court", id);
        }
        return courts.get(0);
    }

    public List<Court> getAll() {
         return crudRepository.findByNamedQuery(Court.FIND_ALL_QUERY, Court.class, null);
    }

    public Court update(Court courtToUpdate) {
        if (courtToUpdate.getSurface() != null) {
            surfaceService.getById(courtToUpdate.getSurface().getId());
        }
        Court updatedCourt = crudRepository.update(courtToUpdate);
        if (updatedCourt == null) {
            System.out.println("ERROR");
        }
        return updatedCourt;
    }

    public void delete(Long id) {
        Court courtToDelete = getById(id);
        Set<Reservation> updatedReservations = new HashSet<>();
        for (Reservation reservation : courtToDelete.getReservations()) {
            reservation.setTotalPrice(0);
            reservation.setCourt(null);
            updatedReservations.add(reservation);
        }
        courtToDelete.setReservations(updatedReservations);
        courtToDelete.setDeleted(true);
        update(courtToDelete);
    }

}
