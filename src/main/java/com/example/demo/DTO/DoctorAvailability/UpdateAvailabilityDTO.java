package com.example.demo.DTO.DoctorAvailability;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class UpdateAvailabilityDTO {
    @NotNull
    private int id;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
}
