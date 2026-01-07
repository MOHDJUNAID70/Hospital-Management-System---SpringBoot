package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class UpdateAvailabilityDTO {
    @NotNull
    private Integer id;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
}
