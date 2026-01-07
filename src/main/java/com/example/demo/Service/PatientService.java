package com.example.demo.Service;

import com.example.demo.Model.Patient;
import com.example.demo.Repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepo patientRepo;
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
}
