package com.example.inqool_task.controller;

import com.example.inqool_task.data.dto.CourtSurfaceRequestDto;
import com.example.inqool_task.data.dto.CourtSurfaceResponseDto;
import com.example.inqool_task.facade.CourtSurfaceFacade;
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
public class CourtSurfaceControllerTest {

    @Mock
    CourtSurfaceFacade surfaceFacade;

    @InjectMocks
    CourtSurfaceController courtSurfaceController;

    @Test
    void createSurface_creationSuccessful_returnsResponseEntityCreated() {
        CourtSurfaceRequestDto surfaceRequestDto = TestDataFactory.courtSurfaceRequestDto;
        CourtSurfaceResponseDto surfaceResponseDto = TestDataFactory.courtSurfaceResponseDto;
        Mockito.when(surfaceFacade.create(surfaceRequestDto)).thenReturn(surfaceResponseDto);

        ResponseEntity<CourtSurfaceResponseDto> surfaceDtoResponseEntity =
                courtSurfaceController.createSurface(surfaceRequestDto);

        assertThat(surfaceDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(surfaceDtoResponseEntity.getBody()).isEqualTo(surfaceResponseDto);
    }

    @Test
    void findById_surfaceFound_returnsResponseEntityOk() {
        CourtSurfaceResponseDto surfaceResponseDto = TestDataFactory.courtSurfaceResponseDto;
        Mockito.when(surfaceFacade.getById(1L)).thenReturn(surfaceResponseDto);

        ResponseEntity<CourtSurfaceResponseDto> surfaceDtoResponseEntity = courtSurfaceController.getSurfaceById(1L);

        assertThat(surfaceDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(surfaceDtoResponseEntity.getBody()).isEqualTo(surfaceResponseDto);
    }

    @Test
    void getAllSurfaces_someSurfacesListed_returnsResponseEntityOk() {
        CourtSurfaceResponseDto surfaceResponseDto1 = TestDataFactory.courtSurfaceResponseDto;
        CourtSurfaceResponseDto surfaceResponseDto2 = TestDataFactory.courtSurfaceResponseDto;
        List<CourtSurfaceResponseDto> surfaceResponseDtoList = List.of(surfaceResponseDto1, surfaceResponseDto2);
        Mockito.when(surfaceFacade.getAll()).thenReturn(surfaceResponseDtoList);

        ResponseEntity<List<CourtSurfaceResponseDto>> surfaceDtoResponseEntity = courtSurfaceController.getAllSurfaces();

        assertThat(surfaceDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(surfaceDtoResponseEntity.getBody()).isEqualTo(surfaceResponseDtoList);
    }

    @Test
    void updateSurface_updateSuccessful_returnsResponseEntityOk() {
        CourtSurfaceRequestDto surfaceRequestDto = TestDataFactory.courtSurfaceRequestDto;
        CourtSurfaceResponseDto surfaceResponseDto = TestDataFactory.courtSurfaceResponseDto;
        Mockito.when(surfaceFacade.update(1L, surfaceRequestDto)).thenReturn(surfaceResponseDto);

        ResponseEntity<CourtSurfaceResponseDto> surfaceDtoResponseEntity =
                courtSurfaceController.updateSurface(1L, surfaceRequestDto);

        assertThat(surfaceDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(surfaceDtoResponseEntity.getBody()).isEqualTo(surfaceResponseDto);
    }

    @Test
    void deleteSurface_deletionSuccessful_returnsResponseNoContent() {
        Mockito.doNothing().when(surfaceFacade).delete(1L);

        ResponseEntity<Void> surfaceDtoResponseEntity = courtSurfaceController.deleteSurface(1L);

        assertThat(surfaceDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
