package com.example.demo.Pagination;

import com.example.demo.Enum.WorkingDay;
import com.example.demo.Model.DTO.DoctorAvailabilityDTO;
import com.example.demo.Service.DoctorAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@RequestMapping("hospital")
public class DoctorAvailabilityPage {
    @Autowired
    DoctorAvailabilityService doctorAvailabilityService;

    @GetMapping("/doctor_availability/details")
    public Page<DoctorAvailabilityDTO> getDoctorAvailability(
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "4") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) WorkingDay workingDay,
            @RequestParam(required = false) LocalTime startTime,
            @RequestParam(required = false) LocalTime endTime
            ) {
        Sort sort=null;
        if(sortDirection.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }
        else sort=Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo-1,pageSize,sort);
        return doctorAvailabilityService.fetchAll(pageable, workingDay, startTime, endTime);
    }
}
