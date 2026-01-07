package com.example.demo.Mapper;

import com.example.demo.Model.DTO.DoctorDTO;
import com.example.demo.Model.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDTO ToDTO(Doctor doctor);
    Doctor ToEntity(DoctorDTO doctorDTO);
}
