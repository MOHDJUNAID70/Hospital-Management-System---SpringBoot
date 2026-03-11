package com.example.demo.DTO;

import com.example.demo.DTO.Doctor.DoctorDTO;
import com.example.demo.DTO.Patient.PatientDTO;
import com.example.demo.Enum.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private Integer id;
    private DoctorDTO doctorInfo;
    private PatientDTO patientInfo;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private LocalTime appointmentEndTime;
    private AppointmentStatus status;
}
