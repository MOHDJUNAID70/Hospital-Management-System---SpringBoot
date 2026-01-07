package com.example.demo.Repository;

import com.example.demo.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {
    @Transactional
    Patient findPatientByageAndName(int age, String name);

    @Transactional
    List<Patient> getPatientsByAge(int age);
}
