package com.example.inqool_task.service;

import com.example.inqool_task.data.model.CourtSurface;
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
public class CourtSurfaceServiceTest {

    @Mock
    private CrudRepository crudRepository;

    @InjectMocks
    private CourtSurfaceService surfaceService;

    @Test
    void createSurface_creationSuccessful_returnsSurface() {
        CourtSurface surface = TestDataFactory.courtSurfaceEntity;
        Map<String, Object> params = Collections.singletonMap("surface", surface.getSurface());

        Mockito.when(crudRepository.findByNamedQuery(CourtSurface.FIND_BY_SURFACE, CourtSurface.class, params))
                .thenReturn(new ArrayList<>());
        Mockito.when(crudRepository.create(surface)).thenReturn(surface);

        CourtSurface surfaceReturned = surfaceService.create(surface);

        assertThat(surfaceReturned).isEqualTo(surface);
    }

    @Test
    void createSurface_surfaceExists_throwsException() {
        CourtSurface surface = TestDataFactory.courtSurfaceEntity;
        Map<String, Object> params = Collections.singletonMap("surface", surface.getSurface());
        List<CourtSurface> surfaces = List.of(surface);

        Mockito.when(crudRepository.findByNamedQuery(CourtSurface.FIND_BY_SURFACE, CourtSurface.class, params))
                .thenReturn(surfaces);

        assertThrows(IllegalArgumentException.class, () -> surfaceService.create(surface));
    }

    @Test
    void findById_surfaceFound_returnsSurface() {
        CourtSurface surface = TestDataFactory.courtSurfaceEntity;
        Map<String, Object> params = Collections.singletonMap("id", surface.getId());
        List<CourtSurface> surfaces = List.of(surface);

        Mockito.when(crudRepository.findByNamedQuery(CourtSurface.FIND_BY_ID_QUERY, CourtSurface.class, params))
                .thenReturn(surfaces);

        CourtSurface surfaceReturned = surfaceService.getById(1L);

        assertThat(surfaceReturned).isEqualTo(surface);
    }

    @Test
    void findById_nullId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> surfaceService.getById(null));
    }

    @Test
    void findById_surfaceNotFound_throwsException() {
        CourtSurface surface = TestDataFactory.courtSurfaceEntity;
        Map<String, Object> params = Collections.singletonMap("id", surface.getId());

        Mockito.when(crudRepository.findByNamedQuery(CourtSurface.FIND_BY_ID_QUERY, CourtSurface.class, params))
                .thenReturn(new ArrayList<>());

        assertThrows(EntityNotFoundException.class, () -> surfaceService.getById(1L));
    }

    @Test
    void getAllSurfaces_someSurfacesListed_returnsListOfSurfaces() {
        CourtSurface surface = TestDataFactory.courtSurfaceEntity;
        List<CourtSurface> surfaces = List.of(surface);

        Mockito.when(crudRepository.findByNamedQuery(CourtSurface.FIND_ALL_QUERY, CourtSurface.class, null))
                .thenReturn(surfaces);

        List<CourtSurface> courtSurfacesReturned = surfaceService.getAll();

        assertThat(courtSurfacesReturned).isEqualTo(surfaces);
    }

    @Test
    void updateSurface_surfaceFound_returnsSurfaceDto() {
        CourtSurface surface = TestDataFactory.courtSurfaceEntity;
        Map<String, Object> params = Collections.singletonMap("id", surface.getId());
        List<CourtSurface> surfaces = List.of(surface);

        Mockito.when(crudRepository.findByNamedQuery(CourtSurface.FIND_BY_ID_QUERY, CourtSurface.class, params))
                .thenReturn(surfaces);
        Mockito.when(crudRepository.update(surface)).thenReturn(surface);

        CourtSurface surfaceReturned = surfaceService.update(surface);

        assertThat(surfaceReturned).isEqualTo(surface);
    }

    @Test
    void deleteSurface_surfaceFound_returnsVoid() {
        CourtSurface surface = TestDataFactory.courtSurfaceEntity;
        Map<String, Object> params = Collections.singletonMap("id", surface.getId());
        List<CourtSurface> surfaces = List.of(surface);

        Mockito.when(crudRepository.findByNamedQuery(CourtSurface.FIND_BY_ID_QUERY, CourtSurface.class, params))
                .thenReturn(surfaces);
        Mockito.when(crudRepository.update(surface)).thenReturn(surface);

        surfaceService.delete(1L);

        verify(crudRepository, times(1)).findByNamedQuery(CourtSurface.FIND_BY_ID_QUERY, CourtSurface.class, params);
        verify(crudRepository, times(1)).update(surface);
    }

}
