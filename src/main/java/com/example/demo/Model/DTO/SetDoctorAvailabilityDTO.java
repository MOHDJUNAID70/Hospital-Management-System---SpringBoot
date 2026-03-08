package com.example.demo.Model.DTO;


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
