package com.example.demo.Controller;

import com.example.demo.Model.DTO.DoctorAvailabilityDTO;
import com.example.demo.Model.DTO.UpdateAvailabilityDTO;
import com.example.demo.Model.DoctorAvailability;
import com.example.demo.Repository.DoctorAvailabilityRepo;
import com.example.demo.Service.DoctorAvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hospital")
@Tag(name = "Doctor Availability APIs")
public class DoctorAvailabilityController {
    @Autowired
    private DoctorAvailabilityService doctorAvailabilityService;

    @Autowired
    private DoctorAvailabilityRepo doctorAvailabilityRepo;

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
        return doctorAvailabilityService.getAvailabilityByDoctorId(id);
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
}
