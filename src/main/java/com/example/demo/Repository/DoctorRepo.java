package com.example.demo.Repository;

import com.example.demo.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByexperienceInYears(int experienceInYears);
    @Transactional
    void deleteByExperienceInYears(int experienceInYears);
}
