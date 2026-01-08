package com.example.demo.Pagination;

import com.example.demo.Enum.AppointmentStatus;
import com.example.demo.Mapper.AppointMapper;
import com.example.demo.Model.DTO.AppointmentDTO;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("hospital")
public class AppointmentPage {
    @Autowired
    AppointmentRepo appointmentRepo;
    @Autowired
    private AppointMapper appointMapper;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("appointment/details")
    public Page<AppointmentDTO> getAppointments(
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "4") int pageSize,
            @RequestParam(required = false, defaultValue = "appointmentDate") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) LocalDate appointmentDate,
            @RequestParam(required = false) AppointmentStatus status,
            @RequestParam(required = false) LocalTime appointmentStartTime,
            @RequestParam(required = false) LocalTime appointmentEndTime
            ){
        Sort sort=null;
        if(sortDirection.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }
        else sort=Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNo-1,pageSize,sort);
        return appointmentService.fetchAll(pageable, appointmentDate, status, appointmentStartTime, appointmentEndTime);
    }
}
