package com.example.demo.Repository;

import com.example.demo.Enum.DoctorSpecializations;
import com.example.demo.Model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer>, JpaSpecificationExecutor<Doctor> {
    List<Doctor> findByexperienceInYears(int experienceInYears);
    @Transactional
    void deleteByExperienceInYears(int experienceInYears);

    @Transactional
    List<Doctor> findBySpecialization(DoctorSpecializations specialization);

    @Transactional
    Page<Doctor> findByName(Pageable pageable, String name);
}
