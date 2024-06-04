package com.example.inqool_task.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreateCourtDto {
    private Long courtNumber;
    private Long surfaceId;
}
