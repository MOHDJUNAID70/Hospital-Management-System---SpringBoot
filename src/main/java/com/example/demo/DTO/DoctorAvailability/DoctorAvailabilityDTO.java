package com.example.demo.DTO.DoctorAvailability;

import com.example.demo.DTO.Doctor.DoctorDTO;
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
