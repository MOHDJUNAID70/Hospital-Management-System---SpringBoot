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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = "Update doctor's Profile", description = "update doctor's Info")
    @PutMapping("doctor/update_profile")
    public ResponseEntity<String> updateDoctorInfo(@Valid @RequestBody Doctor doctor){
        doctorService.updateDoctorInfo(doctor);
        return new ResponseEntity<>("Doctor Info Updated", HttpStatus.OK);
    }
}
