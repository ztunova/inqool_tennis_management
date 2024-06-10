package com.example.inqool_task.data.dto;

import com.example.inqool_task.data.model.GameType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReservationRequestDto {

    @NotNull(message = "Court must be specified")
    @Positive(message = "Court ID can not be negative")
    private Long courtId;

    @NotNull(message = "Game type must be specified")
    @Pattern(regexp = "SINGLES_MATCH|DOUBLES_MATCH", message = "Contact type should be SINGLES_MATCH or DOUBLES_MATCH")
    private String gameType;

    @NotNull(message = "Start time of reservation must be specified")
    private String reservationStart;

    @NotNull(message = "End time of reservation be specified")
    private String reservationEnd;

    @NotNull(message = "Customer must be specified")
    @Valid
    private CustomerDto customer;
}
