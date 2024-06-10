package com.example.inqool_task.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CustomerDto {

    @NotNull(message = "Customer name must be specified")
    @NotBlank(message = "Customer name cannot be blank")
    @NotEmpty(message = "Customer name cannot be empty")
    private String name;

    @NotNull(message = "Phone number must be specified")
    @NotBlank(message = "Phone number cannot be blank")
    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp="[\\d]{10}", message = "Phone number should consist only of digits")
    private String phoneNumber;
}
