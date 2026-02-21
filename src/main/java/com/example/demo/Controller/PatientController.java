package com.example.demo.Controller;

import com.example.demo.Model.Patient;
import com.example.demo.Service.PatientService;
import com.example.demo.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hospital")
@Tag(name = "Patient APIs")
public class PatientController {

    @Autowired
    PatientService patientService;
    @Autowired
    private UserService userService;

    //    get all patient info
    @GetMapping("patient/info")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

//    Create Patient
    @PreAuthorize("hasRole('Patient')")
    @PostMapping("patient/add")
    public ResponseEntity<String> addPatient(@Valid @RequestBody Patient patient) {
        userService.addPatient(patient);
//        patientService.addPatient(patient);
        return new ResponseEntity<>("Your Registration has been done!!!", HttpStatus.CREATED);
    }

//    get patient info by ID
    @GetMapping("patient/id")
    public Patient getPatientById(@RequestParam("id") int id) {
        return patientService.getPatientById(id);
    }

//    Get All Patients whose age is same
    @GetMapping("patient/getAllByAge")
    public List<Patient> getPatientsByAge(@RequestParam int age) {
        return patientService.getPatientsByAge(age);
    }

//    Get Patient details by age and name
    @GetMapping("patient/searchByageAndName")
    public Patient findPatientByageandname(@RequestParam int age, @RequestParam String name) {
        return patientService.findPatientByageAndName(age, name);
    }

//    Delete patient's details by ID
    @DeleteMapping("patient/delete")
    public ResponseEntity<String> deletePatientById(@RequestParam int id) {
        patientService.deletePatientById(id);
        return new ResponseEntity<>("Patient details has been deleted!!!", HttpStatus.OK);
    }
}
