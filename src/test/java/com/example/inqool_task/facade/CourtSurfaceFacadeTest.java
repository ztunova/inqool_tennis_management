package com.example.inqool_task.facade;

import com.example.inqool_task.data.Mapper;
import com.example.inqool_task.data.dto.CourtSurfaceRequestDto;
import com.example.inqool_task.data.dto.CourtSurfaceResponseDto;
import com.example.inqool_task.data.model.CourtSurface;
import com.example.inqool_task.exceptions.EntityNotFoundException;
import com.example.inqool_task.service.CourtSurfaceService;
import com.example.inqool_task.util.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CourtSurfaceFacadeTest {

    @Mock
    private CourtSurfaceService surfaceService;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private CourtSurfaceFacade surfaceFacade;

    @Test
    void createSurface_creationSuccessful_returnsSurfaceResponseDto() {
        CourtSurface surface = TestDataFactory.courtSurfaceEntity;
        CourtSurfaceResponseDto surfaceResponseDto = TestDataFactory.courtSurfaceResponseDto;
        CourtSurfaceRequestDto surfaceRequestDto = TestDataFactory.courtSurfaceRequestDto;

        Mockito.when(surfaceService.create(surface)).thenReturn(surface);
        Mockito.when(mapper.mapToDto(surface)).thenReturn(surfaceResponseDto);
        Mockito.when(mapper.mapToEntity(surfaceRequestDto)).thenReturn(surface);

        CourtSurfaceResponseDto surfaceResponseDtoReturned = surfaceFacade.create(surfaceRequestDto);

        assertThat(surfaceResponseDtoReturned).isEqualTo(surfaceResponseDto);
    }


    @Test
    void findById_surfaceFound_returnsSurfaceDto() {
        CourtSurface surface = TestDataFactory.courtSurfaceEntity;
        CourtSurfaceResponseDto surfaceResponseDto = TestDataFactory.courtSurfaceResponseDto;

        Mockito.when(surfaceService.getById(1L)).thenReturn(surface);
        Mockito.when(mapper.mapToDto(surface)).thenReturn(surfaceResponseDto);

        CourtSurfaceResponseDto surfaceResponseDtoReturned = surfaceFacade.getById(1L);

        assertThat(surfaceResponseDtoReturned).isEqualTo(surfaceResponseDto);
    }

    @Test
    void findById_surfaceNotFound_throwsException() {
        Mockito.when(surfaceService.getById(1L)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> surfaceFacade.getById(1L));

        verify(surfaceService, times(1)).getById(1L);
    }

    @Test
    void getAllSurfaces_someSurfacesListed_returnsListOfSurfaceResponseDtos() {
        CourtSurface surface = TestDataFactory.courtSurfaceEntity;
        CourtSurfaceResponseDto surfaceResponseDto = TestDataFactory.courtSurfaceResponseDto;

        List<CourtSurface> surfaces = List.of(surface);
        List<CourtSurfaceResponseDto> surfaceResponseDtos = List.of(surfaceResponseDto);

        Mockito.when(surfaceService.getAll()).thenReturn(surfaces);
        Mockito.when(mapper.mapToDto(surface)).thenReturn(surfaceResponseDto);

        List<CourtSurfaceResponseDto> courtSurfaceResponseDtos = surfaceFacade.getAll();

        assertThat(courtSurfaceResponseDtos).isEqualTo(surfaceResponseDtos);
    }

    @Test
    void updateSurface_surfaceFound_returnsSurfaceDto() {
        CourtSurface surface = TestDataFactory.courtSurfaceEntity;
        CourtSurfaceRequestDto surfaceRequestDto = TestDataFactory.courtSurfaceRequestDto;
        CourtSurfaceResponseDto surfaceResponseDto = TestDataFactory.courtSurfaceResponseDto;

        Mockito.when(surfaceService.update(surface)).thenReturn(surface);
        Mockito.when(mapper.mapToDto(surface)).thenReturn(surfaceResponseDto);
        Mockito.when(mapper.mapToEntity(surfaceRequestDto)).thenReturn(surface);

        CourtSurfaceResponseDto returnedSurfaceResponseDto = surfaceFacade.update(1L, surfaceRequestDto);

        assertThat(returnedSurfaceResponseDto).isEqualTo(surfaceResponseDto);
    }

    @Test
    void updateSurface_surfacedNotFound_throwsException() {
        CourtSurface surface = TestDataFactory.courtSurfaceEntity;
        CourtSurfaceRequestDto surfaceRequestDto = TestDataFactory.courtSurfaceRequestDto;
        Mockito.when(surfaceService.update(surface)).thenThrow(new EntityNotFoundException());
        Mockito.when(mapper.mapToEntity(surfaceRequestDto)).thenReturn(surface);

        assertThrows(EntityNotFoundException.class, () -> surfaceFacade.update(1L, surfaceRequestDto));

        verify(surfaceService, times(1)).update(surface);
    }

    @Test
    void deleteSurface_surfaceFound_returnsVoid() {
        Mockito.doNothing().when(surfaceService).delete(1L);

        surfaceFacade.delete(1L);

        verify(surfaceService, times(1)).delete(1L);
    }

    @Test
    void deleteSurface_surfacedNotFound_throwsException() {
        assertThrows(Exception.class, () -> {
            doThrow().when(surfaceService).delete(1L);
        });

        surfaceFacade.delete(1L);

        verify(surfaceService, times(1)).delete(1L);
    }

}
