package com.example.demo.Service;

import com.example.demo.Mapper.PatientMapper;
import com.example.demo.Model.DTO.PatientDTO;
import com.example.demo.Model.Patient;
import com.example.demo.Repository.PatientRepo;
import com.example.demo.Specification.PatientSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepo patientRepo;
    @Autowired
    private PatientMapper patientMapper;

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public void addPatient(Patient patient) {
        patientRepo.save(patient);
    }

    public Patient getPatientById(int id) {
        return patientRepo.findById(id).get();
    }

    public Patient findPatientByageAndName(int age, String name) {
        return patientRepo.findPatientByageAndName(age, name);
    }

    public List<Patient> getPatientsByAge(int age) {
        return patientRepo.getPatientsByAge(age);
    }

    public void deletePatientById(int id) {
        patientRepo.deleteById(id);
    }

    public Page<PatientDTO> fetchAll(Pageable pageable, String name, String address, Integer age) {
        Specification<Patient> spec= PatientSpecification.getSpecification(name, address, age);
        return patientRepo.findAll(spec, pageable).map(patientMapper::ToDTO);
    }
}
