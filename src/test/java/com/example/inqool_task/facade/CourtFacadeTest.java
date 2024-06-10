package com.example.inqool_task.facade;

import com.example.inqool_task.data.Mapper;
import com.example.inqool_task.data.dto.CourtRequestDto;
import com.example.inqool_task.data.dto.CourtResponseDto;
import com.example.inqool_task.data.model.Court;
import com.example.inqool_task.exceptions.EntityNotFoundException;
import com.example.inqool_task.service.CourtService;
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
public class CourtFacadeTest {

    @Mock
    private CourtService courtService;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private CourtFacade courtFacade;

    @Test
    void createCourt_creationSuccessful_returnsCourtResponseDto() {
        Court court = TestDataFactory.courtEntity;
        CourtResponseDto courtResponseDto = TestDataFactory.courtResponseDto;
        CourtRequestDto courtRequestDto = TestDataFactory.courtRequestDto;

        Mockito.when(courtService.create(court, 1L)).thenReturn(court);
        Mockito.when(mapper.mapToDto(court)).thenReturn(courtResponseDto);
        Mockito.when(mapper.mapToEntity(courtRequestDto)).thenReturn(court);

        CourtResponseDto courtResponseDtoReturned = courtFacade.create(courtRequestDto);

        assertThat(courtResponseDtoReturned).isEqualTo(courtResponseDto);
    }


    @Test
    void findById_courtFound_returnsCourtDto() {
        Court court = TestDataFactory.courtEntity;
        CourtResponseDto courtResponseDto = TestDataFactory.courtResponseDto;

        Mockito.when(courtService.getById(1L)).thenReturn(court);
        Mockito.when(mapper.mapToDto(court)).thenReturn(courtResponseDto);

        CourtResponseDto courtResponseDtoReturned = courtFacade.getById(1L);

        assertThat(courtResponseDtoReturned).isEqualTo(courtResponseDto);
    }

    @Test
    void findById_courtNotFound_throwsException() {
        Mockito.when(courtService.getById(1L)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> courtFacade.getById(1L));

        verify(courtService, times(1)).getById(1L);
    }

    @Test
    void getAllCourts_someCourtsListed_returnsListOfCourtResponseDtos() {
        Court court = TestDataFactory.courtEntity;
        CourtResponseDto courtResponseDto = TestDataFactory.courtResponseDto;

        List<Court> surfaces = List.of(court);
        List<CourtResponseDto> surfaceResponseDtos = List.of(courtResponseDto);

        Mockito.when(courtService.getAll()).thenReturn(surfaces);
        Mockito.when(mapper.mapToDto(court)).thenReturn(courtResponseDto);

        List<CourtResponseDto> courtResponseDtos = courtFacade.getAll();

        assertThat(courtResponseDtos).isEqualTo(surfaceResponseDtos);
    }

    @Test
    void updateCourt_courtFound_returnsCourtDto() {
        Court court = TestDataFactory.courtEntity;
        CourtRequestDto courtRequestDto = TestDataFactory.courtRequestDto;
        CourtResponseDto courtResponseDto = TestDataFactory.courtResponseDto;

        Mockito.when(courtService.update(1L, court)).thenReturn(court);
        Mockito.when(mapper.mapToDto(court)).thenReturn(courtResponseDto);
        Mockito.when(mapper.mapToEntity(courtRequestDto)).thenReturn(court);

        CourtResponseDto returnedCourtResponseDto = courtFacade.update(1L, courtRequestDto);

        assertThat(returnedCourtResponseDto).isEqualTo(courtResponseDto);
    }

    @Test
    void updateCourt_courtNotFound_throwsException() {
        Court court = TestDataFactory.courtEntity;
        CourtRequestDto courtRequestDto = TestDataFactory.courtRequestDto;
        Mockito.when(courtService.update(1L, court)).thenThrow(new EntityNotFoundException());
        Mockito.when(mapper.mapToEntity(courtRequestDto)).thenReturn(court);

        assertThrows(EntityNotFoundException.class, () -> courtFacade.update(1L, courtRequestDto));

        verify(courtService, times(1)).update(1L, court);
    }

    @Test
    void deleteCourt_courtFound_returnsVoid() {
        Mockito.doNothing().when(courtService).delete(1L);

        courtFacade.delete(1L);

        verify(courtService, times(1)).delete(1L);
    }

    @Test
    void deleteCourt_courtNotFound_throwsException() {
        assertThrows(Exception.class, () -> {
            doThrow().when(courtService).delete(1L);
        });

        courtFacade.delete(1L);

        verify(courtService, times(1)).delete(1L);
    }

}
