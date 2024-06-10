package com.example.inqool_task.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourtSurfaceRequestDto {
    private String surface;
    private double pricePerMinute;
}
