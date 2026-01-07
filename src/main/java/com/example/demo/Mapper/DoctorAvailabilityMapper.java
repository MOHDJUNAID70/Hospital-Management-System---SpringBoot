package com.example.demo.Mapper;

import com.example.demo.Model.DTO.DoctorAvailabilityDTO;
import com.example.demo.Model.DoctorAvailability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorAvailabilityMapper {
    @Mapping(source = "doctor", target="doctorInfo")
    DoctorAvailabilityDTO ToDTO(DoctorAvailability doctorAvailability);
}
