package com.example.demo.Service;

import com.example.demo.ExceptionHandler.CustomException;
import com.example.demo.Mapper.PatientMapper;
import com.example.demo.Model.DTO.PatientDTO;
import com.example.demo.Model.Patient;
import com.example.demo.Repository.PatientRepo;
import com.example.demo.Specification.PatientSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    @Transactional
    public void addPatient(Patient patient) {
        patientRepo.save(patient);
    }

    public Patient getPatientById(int id) {
        return patientRepo.findById(id).orElseThrow(()-> new CustomException("No such patient with id " + id));
    }

    public Patient findPatientByageAndName(int age, String name) {
        Patient patient=patientRepo.findPatientByAgeAndName(age, name);
        if(patient==null){
            throw new CustomException("No such patient exists with age " + age + " and name " + name);
        }
        return patient;
    }

    public List<Patient> getPatientsByAge(int age) {
        List<Patient> patients=patientRepo.getPatientsByAge(age);
        if(patients.isEmpty()){
            throw new CustomException("No such patient with age " + age);
        }
        return patients;
    }

    public void deletePatientById(int id) {
        patientRepo.findById(id).orElseThrow(()-> new CustomException("No such patient Exists with id " + id));
        patientRepo.deleteById(id);
    }

    public Page<PatientDTO> fetchAll(Pageable pageable, String name, String address, Integer StartAge, Integer EndAge) {
        Specification<Patient> spec= PatientSpecification.getSpecification(name, address, StartAge, EndAge);
        List<Patient> patients=patientRepo.findAll(spec);
        if(patients.isEmpty()){
            throw new CustomException("No such patient exists with given criteria");
        }
        return patientRepo.findAll(spec, pageable).map(patientMapper::ToDTO);
    }
}
