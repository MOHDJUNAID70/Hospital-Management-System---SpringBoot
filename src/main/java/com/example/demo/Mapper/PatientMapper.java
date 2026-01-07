package com.example.demo.Mapper;

import com.example.demo.Model.DTO.PatientDTO;
import com.example.demo.Model.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDTO ToDTO(Patient patient);
}
