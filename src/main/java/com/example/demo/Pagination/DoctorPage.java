package com.example.demo.Pagination;

import com.example.demo.Enum.DoctorSpecializations;
import com.example.demo.Model.DTO.DoctorDTO;
import com.example.demo.Service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hospital")
@Tag(name = "Doctor Pagination APIs")
public class DoctorPage {
    @Autowired
    DoctorService doctorService;

    @Operation(summary = "Get all doctors", description = "Fetch all doctors with pagination and Filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @GetMapping("doctor/details")
    public Page<DoctorDTO> GetAllDoctors(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                      @RequestParam(required = false, defaultValue = "4") int pageSize,
                                      @RequestParam(required = false, defaultValue = "id") String sortBy,
                                      @RequestParam(required = false) Integer StartExperienceInYears,
                                      @RequestParam(required = false) Integer EndExperienceInYears,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) DoctorSpecializations specializations
                                         ) {
        Sort sort=Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(pageNo-1,pageSize,sort);
//        if(search==null){
//            return doctorRepo.findAll(pageable).map(doctorMapper::ToDTO);
//        }
//        else return doctorRepo.findByName(pageable, search).map(doctorMapper::ToDTO);
        return doctorService.fetchAll(pageable, StartExperienceInYears, EndExperienceInYears, name, specializations);
    }
}