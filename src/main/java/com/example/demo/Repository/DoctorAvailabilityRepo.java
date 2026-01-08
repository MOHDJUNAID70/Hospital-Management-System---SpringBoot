package com.example.demo.Repository;

import com.example.demo.Enum.WorkingDay;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.DoctorAvailability;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;


@Repository
public interface DoctorAvailabilityRepo extends JpaRepository<DoctorAvailability, Integer>, JpaSpecificationExecutor<DoctorAvailability> {

    @Transactional
    List<DoctorAvailability> findByDoctorId(int id);

    @Transactional
    Optional<DoctorAvailability> findByDoctorAndWorkingDay(Doctor doctor, WorkingDay workingDay);
}
