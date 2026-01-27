package com.example.demo.Repository;

import com.example.demo.Enum.Gender;
import com.example.demo.Model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer>, JpaSpecificationExecutor<Patient> {

    Patient findPatientByAgeAndName(int age, String name);

    List<Patient> getPatientsByAge(int age);

    @Transactional
    Page<Patient> findByGender(Pageable pageable, Gender gender, Specification<Patient> specification);


    @Transactional
    List<Patient> findByAgeIsGreaterThanEqual(Integer age);
}
