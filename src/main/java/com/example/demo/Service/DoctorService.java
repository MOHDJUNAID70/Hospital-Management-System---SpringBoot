package com.example.demo.Service;

import com.example.demo.Enum.DoctorSpecializations;
import com.example.demo.ExceptionHandler.CustomException;
import com.example.demo.Model.DTO.DoctorDTO;
import com.example.demo.Mapper.DoctorMapper;
import com.example.demo.Model.Doctor;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorAvailabilityRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Specification.DoctorSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
        List<Doctor> doctor=doctorRepo.findByexperienceInYears(experience);
        if(doctor.isEmpty()){
            throw new CustomException("No Doctor is available with this experience");
        }
        return doctor.stream().map(doctorMapper::ToDTO).toList();
    }

    public void deleteById(int id) {
        doctorRepo.findById(id).orElseThrow(()-> new CustomException("No Doctor is available with this id"));
        doctorRepo.deleteById(id);
    }

    public void deleteByExperienceInYears(int experience) {
        doctorRepo.deleteByExperienceInYears(experience);
    }

    public List<Doctor> getBySpecialization(DoctorSpecializations specialization) {
        List<Doctor> doctor=doctorRepo.findBySpecialization( specialization );
        return doctor;
    }

    public Page<DoctorDTO> fetchAll(Pageable pageable, Integer StartExperienceInYears, Integer endExperienceInYears, String name,
                                    DoctorSpecializations specializations) {
        Specification<Doctor> spec= DoctorSpecification.getSpecification(StartExperienceInYears, endExperienceInYears, name, specializations);
        return doctorRepo.findAll(spec, pageable).map(doctorMapper::ToDTO);
    }

    public void updateDoctorInfo(@Valid Doctor doctor) {
        Doctor doc=doctorRepo.findById(doctor.getId()).orElseThrow(()-> new RuntimeException("Doctor Not Found"));
        doc.setName(doctor.getName());
        doc.setGender(doctor.getGender());
        doc.setExperienceInYears(doctor.getExperienceInYears());
        doc.setSpecialization(doctor.getSpecialization());
        doctorRepo.save(doc);
    }
}
