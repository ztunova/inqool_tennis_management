package com.example.inqool_task.service;

import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.exceptions.EntityNotFoundException;
import com.example.inqool_task.repository.CrudRepository;
import com.example.inqool_task.util.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CourtServiceTest {

    @Mock
    private CrudRepository crudRepository;

    @Mock
    private CourtSurfaceService surfaceService;

    @InjectMocks
    private CourtService courtService;

    @Test
    void createCourt_creationSuccessful_returnsCourt() {
        Court court = TestDataFactory.courtEntity;
        Map<String, Object> params = Collections.singletonMap("courtNumber", court.getCourtNumber());

        Mockito.when(crudRepository.findByNamedQuery(Court.FIND_BY_COURT_NUMBER, Court.class, params))
                .thenReturn(new ArrayList<>());
        Mockito.when(surfaceService.getById(1L)).thenReturn(TestDataFactory.courtSurfaceEntity);
        Mockito.when(crudRepository.create(court)).thenReturn(court);

        Court courtReturned = courtService.create(court, 1L);

        assertThat(courtReturned).isEqualTo(court);
    }

    @Test
    void createCourt_courtExists_throwsException() {
        Court court = TestDataFactory.courtEntity;
        Map<String, Object> params = Collections.singletonMap("courtNumber", court.getCourtNumber());
        List<Court> courts = List.of(court);

        Mockito.when(crudRepository.findByNamedQuery(Court.FIND_BY_COURT_NUMBER, Court.class, params)).thenReturn(courts);

        assertThrows(IllegalArgumentException.class, () -> courtService.create(court, 1L));
    }

    @Test
    void findById_courtFound_returnsSurface() {
        Court court = TestDataFactory.courtEntity;
        Map<String, Object> params = Collections.singletonMap("id", court.getId());
        List<Court> courts = List.of(court);

        Mockito.when(crudRepository.findByNamedQuery(Court.FIND_BY_ID_QUERY, Court.class, params)).thenReturn(courts);

        Court courtReturned = courtService.getById(1L);

        assertThat(courtReturned).isEqualTo(court);
    }

    @Test
    void findById_nullId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> courtService.getById(null));
    }

    @Test
    void findById_courtNotFound_throwsException() {
        Court court = TestDataFactory.courtEntity;
        Map<String, Object> params = Collections.singletonMap("id", court.getId());

        Mockito.when(crudRepository.findByNamedQuery(Court.FIND_BY_ID_QUERY, Court.class, params))
                .thenReturn(new ArrayList<>());

        assertThrows(EntityNotFoundException.class, () -> courtService.getById(1L));
    }

    @Test
    void getAllCourts_someCourtsListed_returnsListOfCourts() {
        Court court = TestDataFactory.courtEntity;
        List<Court> courts = List.of(court);

        Mockito.when(crudRepository.findByNamedQuery(Court.FIND_ALL_QUERY, Court.class, null))
                .thenReturn(courts);

        List<Court> courtsReturned = courtService.getAll();

        assertThat(courtsReturned).isEqualTo(courts);
    }

    @Test
    void updateCourt_courtFound_returnsCourt() {
        Court court = TestDataFactory.courtEntity;
        Map<String, Object> params = Collections.singletonMap("id", court.getId());
        List<Court> courts = List.of(court);

        Mockito.when(crudRepository.findByNamedQuery(Court.FIND_BY_ID_QUERY, Court.class, params))
                .thenReturn(courts);
        Mockito.when(crudRepository.update(court)).thenReturn(court);

        Court courtReturned = courtService.update(1L, court);

        assertThat(courtReturned).isEqualTo(court);
    }

    @Test
    void deleteCourt_courtFound_returnsVoid() {
        Court court = TestDataFactory.courtEntity;
        court.addReservation(TestDataFactory.reservationEntity);
        Map<String, Object> params = Collections.singletonMap("id", court.getId());
        List<Court> courts = List.of(court);

        Mockito.when(crudRepository.findByNamedQuery(Court.FIND_BY_ID_QUERY, Court.class, params))
                .thenReturn(courts);
        Mockito.when(crudRepository.update(court)).thenReturn(court);

        courtService.delete(1L);

        verify(crudRepository, times(1)).findByNamedQuery(Court.FIND_BY_ID_QUERY, Court.class, params);
        verify(crudRepository, times(1)).update(court);
    }

}
