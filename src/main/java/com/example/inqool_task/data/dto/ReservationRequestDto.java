package com.example.inqool_task.data.dto;

import com.example.inqool_task.data.model.GameType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReservationRequestDto {
    private Long courtId;
    private String gameType;
    private String reservationStart;
    private String reservationEnd;
    private CustomerDto customer;
}
