package com.example.demo.Mapper;

import com.example.demo.Model.DTO.AppointmentDTO;
import com.example.demo.Model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {DoctorMapper.class, PatientMapper.class})
public interface AppointMapper {

    @Mapping(source = "doctor", target = "doctorInfo")
    @Mapping(source = "patient", target = "patientInfo")
    @Mapping(source = "appointmentid", target = "id")
    AppointmentDTO ToDTO(Appointment appointment);
}
