package com.example.demo.Repository;

import com.example.demo.Enum.AppointmentStatus;
import com.example.demo.Model.Appointment;
import com.example.demo.Model.DTO.AppointmentDTO;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.Patient;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

//    boolean existsByDoctorAndAppointmentDateAndAppointmentTime(
//            Doctor doctor,
//            LocalDate appointmentDate,
//            LocalTime appointmentTime
//    );

    @Transactional
    boolean existsByDoctorAndAppointmentDateAndAppointmentTimeLessThanAndAppointmentEndTimeGreaterThan(
            Doctor doctor,
            LocalDate appointmentDate,
            LocalTime end,
            LocalTime appointmentTime
    );

    @Transactional
    List<Appointment> findByDoctorId(int Id);

    @Transactional
    List<Appointment> findByappointmentDate(LocalDate appointmentDate);

    @Transactional
    void deleteAppointmentByAppointmentDate(LocalDate date);

    @Transactional
    boolean existsByDoctorAndPatientAndAppointmentDate(Doctor doctor, Patient patient,
                                                       LocalDate appointmentDate);


    @Transactional
    boolean existsByPatientAndAppointmentDateAndAppointmentTimeLessThanAndAppointmentEndTimeGreaterThan(Patient patient,
             @NotNull LocalDate appointmentDate, LocalTime end, LocalTime appointmentTime);

    @Transactional
    Optional< List<Appointment>> findByDoctorAndAppointmentDateAndStatusAndAppointmentTimeAfter(Doctor doctor, LocalDate localDate, AppointmentStatus appointmentStatus, @NotNull LocalTime endTime);
}
