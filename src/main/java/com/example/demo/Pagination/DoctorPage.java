package com.example.demo.Pagination;

import com.example.demo.Enum.DoctorSpecializations;
import com.example.demo.Model.DTO.DoctorDTO;
import com.example.demo.Service.DoctorService;
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
public class DoctorPage {
    @Autowired
    DoctorService doctorService;

    @GetMapping("doctor/details")
    public Page<DoctorDTO> GetAllDoctors(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                      @RequestParam(required = false, defaultValue = "4") int pageSize,
                                      @RequestParam(required = false, defaultValue = "id") String sortBy,
                                      @RequestParam(required = false, defaultValue = "asc") String sortDirection,
                                      @RequestParam(required = false) Integer experienceInYears,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) DoctorSpecializations specializations
                                         ) {
        Sort sort=null;
        if(sortDirection.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }
        else {
            sort=Sort.by(sortBy).descending();
        }
        Pageable pageable= PageRequest.of(pageNo-1,pageSize,sort);
//        if(search==null){
//            return doctorRepo.findAll(pageable).map(doctorMapper::ToDTO);
//        }
//        else return doctorRepo.findByName(pageable, search).map(doctorMapper::ToDTO);
        return doctorService.fetchAll(pageable, experienceInYears, name, specializations);
    }
}