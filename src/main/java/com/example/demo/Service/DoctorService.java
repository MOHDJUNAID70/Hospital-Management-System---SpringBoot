package com.example.demo.Service;

import com.example.demo.Enum.AppointmentStatus;
import com.example.demo.Model.Appointment;
import com.example.demo.Model.DTO.DoctorDTO;
import com.example.demo.Mapper.DoctorMapper;
import com.example.demo.Model.DTO.UpdateAvailabilityDTO;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.DoctorAvailability;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorAvailabilityRepo;
import com.example.demo.Repository.DoctorRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    DoctorRepo doctorRepo;
    @Autowired
    DoctorMapper doctorMapper;

    @Autowired
    DoctorAvailabilityRepo doctorAvailabilityRepo;

    @Autowired
    AppointmentRepo appointmentRepo;

    public List<DoctorDTO> getDoctorinfo() {
        return doctorRepo.findAll().stream().map(doctorMapper::ToDTO).toList();
    }

    public void addDoctor(Doctor doctor) {
        doctorRepo.save(doctor);
    }

    public Doctor GetDoctorById(int id) {
        return doctorRepo.findById(id).orElseThrow(()-> new RuntimeException("Doctor Not Found"));
    }

    public List<DoctorDTO> getByexperienceInYears(int experience) {
        return doctorRepo.findByexperienceInYears(experience).stream().map(doctorMapper::ToDTO).toList();
    }

    public void deleteById(int id) {
        doctorRepo.deleteById(id);
    }

    public void deleteByExperienceInYears(int experience) {
        doctorRepo.deleteByExperienceInYears(experience);
    }

    public void setAvailability(@Valid DoctorAvailability availability) {
        Doctor doctor = doctorRepo.findById(availability.getDoctor().getId()).orElseThrow(()-> new RuntimeException("doctor not found"));
        availability.setDoctor(doctor);
        availability.setDayOfWeek(availability.getDayOfWeek());
        availability.setStartTime(availability.getStartTime());
        availability.setEndTime(availability.getEndTime());
        doctorAvailabilityRepo.save(availability);
    }

    public void updateDoctorAvailability(@Valid UpdateAvailabilityDTO request) {
        DoctorAvailability availability=doctorAvailabilityRepo.findById(request.getId())
                .orElseThrow(()-> new RuntimeException("doctor availability not found"));
        availability.setStartTime(request.getStartTime());
        availability.setEndTime(request.getEndTime());
        doctorAvailabilityRepo.save(availability);
        CancelAffectedAppointments(
                availability.getDoctor(),
                availability.getDayOfWeek(),
                request.getEndTime()
        );
    }
    private void CancelAffectedAppointments(Doctor doctor, @NotNull DayOfWeek dayOfWeek, @NotNull LocalTime endTime) {
        LocalDate localDate = LocalDate.now();
        if(!localDate.getDayOfWeek().equals(dayOfWeek)) {
            throw new RuntimeException("invalid day of week");
        }
        List<Appointment> appointments=appointmentRepo.findByDoctorAndAppointmentDateAndStatusAndAppointmentTimeAfter(
                doctor,localDate, AppointmentStatus.Booked, endTime
        ).orElseThrow(()-> new RuntimeException("appointment not found"));
        for(Appointment appointment : appointments) {
            appointment.setStatus(AppointmentStatus.Cancelled);
        }
        appointmentRepo.saveAll(appointments);
    }
}
