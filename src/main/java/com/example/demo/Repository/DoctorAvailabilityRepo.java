package com.example.demo.Repository;

import com.example.demo.Model.Doctor;
import com.example.demo.Model.DoctorAvailability;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;


@Repository
public interface DoctorAvailabilityRepo extends JpaRepository<DoctorAvailability, Integer> {

    @Transactional
    List<DoctorAvailability> findByDoctorId(int id);

    @Transactional
    Optional<DoctorAvailability> findByDoctorAndDayOfWeek(Doctor doctor, DayOfWeek dayOfWeek);
}
