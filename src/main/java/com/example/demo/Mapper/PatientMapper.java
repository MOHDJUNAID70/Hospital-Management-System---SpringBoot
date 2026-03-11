package com.example.demo.Mapper;

import com.example.demo.DTO.Patient.PatientDTO;
import com.example.demo.Model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(source = "user", target = "userInfo")
    PatientDTO ToDTO(Patient patient);
}
