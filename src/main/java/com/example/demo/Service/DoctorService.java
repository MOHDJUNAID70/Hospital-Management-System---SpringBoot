package com.example.demo.Service;

import com.example.demo.Enum.AppointmentStatus;
import com.example.demo.Enum.DoctorSpecializations;
import com.example.demo.Model.Appointment;
import com.example.demo.Model.DTO.DoctorDTO;
import com.example.demo.Mapper.DoctorMapper;
import com.example.demo.Model.DTO.UpdateAvailabilityDTO;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.DoctorAvailability;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorAvailabilityRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Specification.DoctorSpecification;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    DoctorRepo doctorRepo;
    @Autowired
    DoctorMapper doctorMapper;

    @Autowired
    DoctorAvailabilityRepo doctorAvailabilityRepo;

    @Autowired
    AppointmentRepo appointmentRepo;

    public void addDoctor(Doctor doctor) {
        doctorRepo.save(doctor);
    }

    public Doctor GetDoctorById(int id) {
        return doctorRepo.findById(id).orElseThrow(()-> new RuntimeException("Doctor Not Found"));
    }

    public List<DoctorDTO> getByexperienceInYears(int experience) {
        return doctorRepo.findByexperienceInYears(experience).stream().map(doctorMapper::ToDTO).toList();
    }

    public void deleteById(int id) {
        doctorRepo.deleteById(id);
    }

    public void deleteByExperienceInYears(int experience) {
        doctorRepo.deleteByExperienceInYears(experience);
    }

    public List<Doctor> getBySpecialization(DoctorSpecializations specialization) {
        return doctorRepo.findBySpecialization(specialization);
    }

    public Page<DoctorDTO> fetchAll(Pageable pageable, Integer experienceInYears, String name,
                                    DoctorSpecializations specializations) {
        Specification<Doctor> spec= DoctorSpecification.getSpecification(experienceInYears, name, specializations);
        return doctorRepo.findAll(spec, pageable).map(doctorMapper::ToDTO);
    }
}
