package com.example.demo.Controller;

import com.example.demo.Enum.AppointmentStatus;
import com.example.demo.Mapper.DoctorAvailabilityMapper;
import com.example.demo.Model.Appointment;
import com.example.demo.Model.DTO.DoctorAvailabilityDTO;
import com.example.demo.Model.DTO.DoctorDTO;
import com.example.demo.Model.DTO.UpdateAvailabilityDTO;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.DoctorAvailability;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorAvailabilityRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Service.DoctorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("hospital")
public class DoctorController {

    @Autowired
    DoctorService doctorService;
    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private DoctorAvailabilityRepo doctorAvailabilityRepo;
    @Autowired
    private DoctorAvailabilityMapper doctorAvailabilityMapper;
    @Autowired
    private AppointmentRepo appointmentRepo;

    @GetMapping("/doctor/info")
    public List<DoctorDTO> GetAllDoctors() {
        return doctorService.getDoctorinfo();
    }

    @GetMapping("csrf")
    public CsrfToken GetCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
    @GetMapping("sesid")
    public String GetSessionId(HttpServletRequest request) {
        return request.getSession().getId();
    }

    @PostMapping("/doctor/add")
    public ResponseEntity<String> AddDoctor(@Valid @RequestBody Doctor doctor) {
        doctorService.addDoctor(doctor);
        return new ResponseEntity<>("Doctor Added Successfully", HttpStatus.OK);
    }

    @GetMapping("doctor/{id}")
    public Doctor GetDoctorById(@PathVariable int id) {
        return doctorService.GetDoctorById(id);
    }

    @GetMapping("doctor/withexp/{experience}")
    public List<DoctorDTO> getByexperienceInYears(@PathVariable int experience) {
        return doctorService.getByexperienceInYears(experience);
    }

    @DeleteMapping("doctor/delete/id/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable int id) {
        doctorService.deleteById(id);
        return new ResponseEntity<>("Doctor Info deleted", HttpStatus.OK);
    }

    @DeleteMapping("doctor/delete/exp/{experience}")
    public ResponseEntity<String> deleteByExperienceInYears(@PathVariable int experience) {
        doctorService.deleteByExperienceInYears(experience);
        return new ResponseEntity<>("Doctor Info deleted with that Experience", HttpStatus.OK);
    }

//  set the doctors availability timetable
    @PostMapping("doctor/set_availability")
    public ResponseEntity<String> setAvailability(@Valid @RequestBody DoctorAvailability availability) {
        doctorService.setAvailability(availability);
        return new ResponseEntity<>("Doctor's Availability set", HttpStatus.OK);
    }

//    update the doctor's availability
    @PostMapping("doctor/update_availability")
    public ResponseEntity<String> updateDoctorAvailability(@RequestBody @Valid UpdateAvailabilityDTO request) {
        doctorService.updateDoctorAvailability(request);
        return new ResponseEntity<>("Doctor's Availability updated and " +
                "the appointments have been cancelled which are after the "+request.getEndTime()+" a.m/p.m !!!", HttpStatus.OK);
    }

    // get the doctor's timetable by their id
    @GetMapping("doctor/availability/{id}")
    public List<DoctorAvailabilityDTO> GetAvailabilityById(@PathVariable int id) {
        return doctorAvailabilityRepo.findByDoctorId(id).stream().map(doctorAvailabilityMapper::ToDTO).toList();
    }

    @GetMapping("doctor/availability")
    public List<DoctorAvailability> getAvailabilityById() {
        return doctorAvailabilityRepo.findAll();
    }
}
