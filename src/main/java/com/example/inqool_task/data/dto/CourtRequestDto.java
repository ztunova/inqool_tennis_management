package com.example.inqool_task.data.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CourtRequestDto {

    @NotNull(message = "Court number must be specified")
    private Long courtNumber;

    @NotNull(message = "Surface be specified")
    @Positive(message = "Surface ID can not be negative")
    private Long surfaceId;
}
