package com.example.inqool_task.data.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CourtSurfaceRequestDto {

    @NotNull(message = "Surface must be specified")
    @NotBlank(message = "Surface cannot be blank")
    @NotEmpty(message = "Surface cannot be empty")
    private String surface;

    @NotNull(message = "Price per minute must be specified")
    @PositiveOrZero(message = "Price can not be negative")
    private double pricePerMinute;
}
