package com.example.demo.Model.DTO;

import com.example.demo.Enum.WorkingDay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailabilityDTO {
    private Integer id;
    private DoctorDTO doctorInfo;
    private WorkingDay workingDay;
    private LocalTime startTime;
    private LocalTime endTime;
}
