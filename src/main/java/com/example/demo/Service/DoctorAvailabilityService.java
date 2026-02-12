package com.example.demo.Service;

import com.example.demo.Enum.AppointmentStatus;
import com.example.demo.Enum.WorkingDay;
import com.example.demo.ExceptionHandler.CustomException;
import com.example.demo.Mapper.DoctorAvailabilityMapper;
import com.example.demo.Model.Appointment;
import com.example.demo.Model.DTO.DoctorAvailabilityDTO;
import com.example.demo.Model.DTO.UpdateAvailabilityDTO;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.DoctorAvailability;
import com.example.demo.Redis.RedisCacheService;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorAvailabilityRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Specification.DoctorAvailabilitySpecification;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DoctorAvailabilityService {
    @Autowired
    private DoctorAvailabilityRepo doctorAvailabilityRepo;
    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private DoctorAvailabilityMapper doctorAvailabilityMapper;
    @Autowired
    private RedisCacheService redisCacheService;

    @Transactional
    public void setAvailability(@Valid DoctorAvailability availability) {
        Doctor doctor = doctorRepo.findById(availability.getDoctor().getId()).orElseThrow(()-> new RuntimeException("doctor not found"));
        availability.setDoctor(doctor);
        availability.setWorkingDay(availability.getWorkingDay());
        availability.setStartTime(availability.getStartTime());
        availability.setEndTime(availability.getEndTime());
        doctorAvailabilityRepo.save(availability);
    }


    public void updateDoctorAvailability(@Valid UpdateAvailabilityDTO request) {
        DoctorAvailability availability=doctorAvailabilityRepo.findById(request.getId())
                .orElseThrow(()-> new RuntimeException("doctor availability not found"));
        availability.setStartTime(request.getStartTime());
        availability.setEndTime(request.getEndTime());
        doctorAvailabilityRepo.save(availability);
        CancelAffectedAppointments(
                availability.getDoctor(),
                availability.getWorkingDay(),
                request.getStartTime(),
                request.getEndTime()
        );
    }
    private void CancelAffectedAppointments(Doctor doctor, @NotNull WorkingDay workingDay, @NotNull LocalTime startTime, @NotNull LocalTime endTime) {
        LocalDate localDate = LocalDate.now();
        if(!localDate.getDayOfWeek().name().equals(workingDay.name())) {
            throw new RuntimeException("invalid day of week");
        }
        List<Appointment> appointments=appointmentRepo.findByDoctorAndAppointmentDateAndStatusAndAppointmentTimeBeforeOrAppointmentEndTimeAfter(
                doctor,localDate, AppointmentStatus.Booked, startTime, endTime
        ).orElseThrow(()-> new RuntimeException("appointment not found"));
        if(appointments.isEmpty()) {
            throw new CustomException("No such appointment exists before this time: " + startTime + " and after this time: "+endTime + " and availability has been updated");
        };
        for(Appointment appointment : appointments) {
            appointment.setStatus(AppointmentStatus.Cancelled);
        }
        appointmentRepo.saveAll(appointments);
    }

//    Specification
    public Page<DoctorAvailabilityDTO> fetchAll(Pageable pageable, WorkingDay workingDay, LocalTime startTime, LocalTime endTime) {
        Specification<DoctorAvailability> specification= DoctorAvailabilitySpecification.getSpecification(workingDay,startTime,endTime);
        List<DoctorAvailability> availability=doctorAvailabilityRepo.findAll(specification);
        if(availability.isEmpty()){
            throw new CustomException("No Availability exists with this criteria");
        }
        return doctorAvailabilityRepo.findAll(specification, pageable).map(doctorAvailabilityMapper::ToDTO);
    }

    public List<DoctorAvailabilityDTO> getAvailabilityByDoctorId(int id) {
        String cacheKey=String.valueOf(id);
        List<DoctorAvailabilityDTO> cached=redisCacheService.getDoctorAvailabilityByTheirId(cacheKey);
        if(cached!=null){
            return cached;
        }
        List<DoctorAvailability> availability=doctorAvailabilityRepo.findByDoctorId(id);
        if(availability.isEmpty()){
            throw new CustomException("No Availability exists with this Doctor Id");
        }
        List<DoctorAvailabilityDTO> availabilityDTO=availability.stream().map(doctorAvailabilityMapper::ToDTO).toList();
        redisCacheService.setDoctorAvailabilityByTheirId(cacheKey, availabilityDTO);
        return availabilityDTO;
    }
}
