package com.example.demo.Controller;

import com.example.demo.Enum.DoctorSpecializations;
import com.example.demo.ExceptionHandler.CustomException;
import com.example.demo.Mapper.DoctorAvailabilityMapper;
import com.example.demo.Model.DTO.DoctorAvailabilityDTO;
import com.example.demo.Model.DTO.DoctorDTO;
import com.example.demo.Model.DTO.UpdateAvailabilityDTO;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.DoctorAvailability;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorAvailabilityRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Service.DoctorAvailabilityService;
import com.example.demo.Service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hospital")
@Tag(name = "Doctor APIs")
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
    private DoctorAvailabilityService  doctorAvailabilityService;

    @GetMapping("doctor/info")
    public List<Doctor> DoctorsDetails() {
        return doctorRepo.findAll();
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

    @GetMapping("doctor/specialization/{specialization}")
    public List<Doctor> getBySpecialization(@Valid @PathVariable DoctorSpecializations specialization) {
        return doctorService.getBySpecialization(specialization);
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
        doctorAvailabilityService.setAvailability(availability);
        return new ResponseEntity<>("Doctor's Availability set", HttpStatus.OK);
    }

//    update the doctor's availability
    @PostMapping("doctor/update_availability")
    public ResponseEntity<String> updateDoctorAvailability(@RequestBody @Valid UpdateAvailabilityDTO request) {
        doctorAvailabilityService.updateDoctorAvailability(request);
        return new ResponseEntity<>("Doctor's Availability updated and " +
                "the appointments have been cancelled which are before and after the time "+request.getStartTime()+" and "+ request.getEndTime(), HttpStatus.OK);
    }

    // get the doctor's timetable by their id
    @Operation(summary = "Get Doctor's Timetable", description = "Doctor's Timetable by their ID")
    @GetMapping("/doctor/availability/{id}")
    public List<DoctorAvailabilityDTO> GetAvailabilityById(@PathVariable int id) {
        return doctorAvailabilityRepo.findByDoctorId(id).stream().map(doctorAvailabilityMapper::ToDTO).toList();
    }

    @DeleteMapping("/doctor/delete_availability_by_id")
    public ResponseEntity<String> deleteAvailabilityById(@RequestParam int id) {
        doctorAvailabilityRepo.deleteById(id);
        return new ResponseEntity<>("Doctor's Availability deleted", HttpStatus.OK);
    }
    @GetMapping("doctor/availability")
    public List<DoctorAvailability> getAvailabilityById() {
        return doctorAvailabilityRepo.findAll();
    }

    @Operation(summary = "Update doctor's Profile", description = "update doctor's Info")
    @PutMapping("doctor/update_profile")
    public ResponseEntity<String> updateDoctorInfo(@Valid @RequestBody Doctor doctor){
        doctorService.updateDoctorInfo(doctor);
        return new ResponseEntity<>("Doctor Info Updated", HttpStatus.OK);
    }
}
