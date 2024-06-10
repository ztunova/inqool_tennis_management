package com.example.inqool_task.controller;

import com.example.inqool_task.data.dto.CourtRequestDto;
import com.example.inqool_task.data.dto.CourtResponseDto;
import com.example.inqool_task.facade.CourtFacade;
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
public class CourtControllerTest {

    @Mock
    CourtFacade courtFacade;

    @InjectMocks
    CourtController courtController;

    @Test
    void createCourt_creationSuccessful_returnsResponseEntityCreated() {
        CourtRequestDto courtRequestDto = TestDataFactory.courtRequestDto;
        CourtResponseDto courtResponseDto = TestDataFactory.courtResponseDto;
        Mockito.when(courtFacade.create(courtRequestDto)).thenReturn(courtResponseDto);

        ResponseEntity<CourtResponseDto> courtDtoResponseEntity = courtController.createCourt(courtRequestDto);

        assertThat(courtDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(courtDtoResponseEntity.getBody()).isEqualTo(courtResponseDto);
    }

    @Test
    void findById_courtFound_returnsResponseEntityOk() {
        CourtResponseDto courtResponseDto = TestDataFactory.courtResponseDto;
        Mockito.when(courtFacade.getById(1L)).thenReturn(courtResponseDto);

        ResponseEntity<CourtResponseDto> courtDtoResponseEntity = courtController.getCourtById(1L);

        assertThat(courtDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(courtDtoResponseEntity.getBody()).isEqualTo(courtResponseDto);
    }

    @Test
    void getAllCourts_someCourtsListed_returnsResponseEntityOk() {
        CourtResponseDto courtResponseDto1 = TestDataFactory.courtResponseDto;
        CourtResponseDto courtResponseDto2 = TestDataFactory.courtResponseDto;
        List<CourtResponseDto> courtResponseDtoList = List.of(courtResponseDto1, courtResponseDto2);
        Mockito.when(courtFacade.getAll()).thenReturn(courtResponseDtoList);

        ResponseEntity<List<CourtResponseDto>> courtDtoResponseEntity = courtController.getAllCourts();

        assertThat(courtDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(courtDtoResponseEntity.getBody()).isEqualTo(courtResponseDtoList);
    }

    @Test
    void updateCourt_updateSuccessful_returnsResponseEntityOk() {
        CourtRequestDto courtRequestDto = TestDataFactory.courtRequestDto;
        CourtResponseDto courtResponseDto = TestDataFactory.courtResponseDto;
        Mockito.when(courtFacade.update(1L, courtRequestDto)).thenReturn(courtResponseDto);

        ResponseEntity<CourtResponseDto> courtDtoResponseEntity = courtController.updateCourt(1L, courtRequestDto);

        assertThat(courtDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(courtDtoResponseEntity.getBody()).isEqualTo(courtResponseDto);
    }

    @Test
    void deleteCourt_deletionSuccessful_returnsResponseNoContent() {
        Mockito.doNothing().when(courtFacade).delete(1L);

        ResponseEntity<Void> courtDtoResponseEntity = courtController.deleteCourt(1L);

        assertThat(courtDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
