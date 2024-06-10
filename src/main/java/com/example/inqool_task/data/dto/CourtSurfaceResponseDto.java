package com.example.inqool_task.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CourtSurfaceResponseDto {
    private Long id;
    private String surface;
    private double pricePerMinute;
}
