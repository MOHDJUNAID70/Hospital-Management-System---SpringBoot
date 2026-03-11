package com.example.demo.DTO.DoctorAvailability;


import com.example.demo.Enum.WorkingDay;
import lombok.Data;

import java.time.LocalTime;

@Data
public class SetDoctorAvailabilityDTO {
    private int id;
    private WorkingDay workingDay;
    private LocalTime startTime;
    private LocalTime endTime;
}
