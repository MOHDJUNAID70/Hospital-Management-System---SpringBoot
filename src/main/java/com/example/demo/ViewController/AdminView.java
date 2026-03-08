package com.example.demo.ViewController;

import com.example.demo.Controller.DoctorController;
import com.example.demo.Controller.PatientController;
import com.example.demo.Enum.DoctorSpecializations;
import com.example.demo.ExceptionHandler.CustomException;
import com.example.demo.Model.DTO.PatientDTO;
import com.example.demo.Model.DTO.SetDoctorAvailabilityDTO;
import com.example.demo.Model.DTO.UpdateAvailabilityDTO;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.DoctorAvailability;
import com.example.demo.Model.Patient;
import com.example.demo.Pagination.DoctorPage;
import com.example.demo.Pagination.PatientPage;
import com.example.demo.Repository.DoctorAvailabilityRepo;
import com.example.demo.Service.DoctorAvailabilityService;
import com.example.demo.Service.DoctorService;
import com.example.demo.Service.PatientService;
import com.example.demo.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminView {

    @Autowired
    DoctorController doctorController;

    @Autowired
    PatientService patientService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientPage patientPage;

    @Autowired
    DoctorPage doctorPage;
    @Autowired
    private DoctorAvailabilityService doctorAvailabilityService;
    @Autowired
    private DoctorAvailabilityRepo doctorAvailabilityRepo;

    //    DoctorInfo
    @GetMapping("/doctors/info")
    public String doctorInfo(
            Model model,
            @org.springframework.web.bind.annotation.RequestParam(required = false, defaultValue = "1") int pageNo,
            @org.springframework.web.bind.annotation.RequestParam(required = false, defaultValue = "10") int pageSize,
            @org.springframework.web.bind.annotation.RequestParam(required = false, defaultValue = "id") String sortBy,
            @org.springframework.web.bind.annotation.RequestParam(required = false) Integer minExperience,
            @org.springframework.web.bind.annotation.RequestParam(required = false) Integer maxExperience,
            @org.springframework.web.bind.annotation.RequestParam(required = false) String name,
            @org.springframework.web.bind.annotation.RequestParam(required = false) DoctorSpecializations specialization
    ) {
        Page<Doctor> doctors = doctorPage.GetAllDoctors(pageNo, pageSize, sortBy, minExperience, maxExperience, name, specialization);
        model.addAttribute("doctors", doctors);
        model.addAttribute("name", name);
        model.addAttribute("specialization", specialization);
        model.addAttribute("minExperience", minExperience);
        model.addAttribute("maxExperience", maxExperience);
        return "doctor/doctorInfo";
    }

    @PostMapping("/doctors/add")
    public String addDoctor(@Valid @ModelAttribute Doctor doctor){
        doctorService.addDoctor(doctor);
        return "redirect:/admin/doctors/info";
    }

    @PostMapping("/doctors/edit")
    public String editDoctor(@Valid @ModelAttribute Doctor doctor) {
        doctorService.updateDoctorInfo(doctor);
        return "redirect:/admin/doctors/info";
    }

    @PostMapping("/doctors/delete/{id}")
    public String deleteDoctor(@PathVariable int id) {
        doctorService.deleteById(id);
        return "redirect:/admin/doctors/info";
    }

    @GetMapping("/doctors/{id}/availability")
    public String doctorAvailability(@PathVariable int id, Model model){
        List<DoctorAvailability> availabilities=doctorAvailabilityRepo.findByDoctorId(id);
        if(availabilities == null || availabilities.isEmpty()){
            model.addAttribute("availabilities", Collections.emptyList());
            model.addAttribute("doctorId", id);
            model.addAttribute("errorMessage", "No availability found for this doctor.");
            return "doctor/doctorAvailability";
        }
        model.addAttribute("availabilities", availabilities);
        model.addAttribute("doctorId", id);
        return "doctor/doctorAvailability";
    }

    @PostMapping("/doctors/{id}/update_availability")
    public String updateDoctorAvailability(@Valid @ModelAttribute UpdateAvailabilityDTO request) {
        doctorAvailabilityService.updateDoctorAvailability(request);
        return "redirect:/admin/doctors/{id}/availability";
    }

    @PostMapping("/doctors/{id}/add_availability")
    public String addDoctorAvailability(@ModelAttribute SetDoctorAvailabilityDTO availability) {
        doctorAvailabilityService.setAvailability(availability);
        return "redirect:/admin/doctors/{id}/availability";
    }

//    PatientInfo
@GetMapping("/patients/info")
public String patientInfo(
        Model model,
        @org.springframework.web.bind.annotation.RequestParam(required = false, defaultValue = "1") int pageNo,
        @org.springframework.web.bind.annotation.RequestParam(required = false, defaultValue = "10") int pageSize,
        @org.springframework.web.bind.annotation.RequestParam(required = false, defaultValue = "id") String sortBy,
        @org.springframework.web.bind.annotation.RequestParam(required = false) String name,
        @org.springframework.web.bind.annotation.RequestParam(required = false) String address,
        @org.springframework.web.bind.annotation.RequestParam(required = false) Integer StartAge,
        @org.springframework.web.bind.annotation.RequestParam(required = false) Integer EndAge
) {
    Page<PatientDTO> patients = patientPage.getPatients(pageNo, pageSize, sortBy, name, address, StartAge, EndAge);
    model.addAttribute("patients", patients);
    model.addAttribute("name", name);
    model.addAttribute("address", address);
    model.addAttribute("StartAge", StartAge);
    model.addAttribute("EndAge", EndAge);
    return "patient/patientInfo";
}
}


