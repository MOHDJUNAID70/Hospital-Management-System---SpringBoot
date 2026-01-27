package com.example.demo.Service;

import com.example.demo.Enum.AppointmentStatus;
import com.example.demo.Enum.WorkingDay;
import com.example.demo.ExceptionHandler.CustomException;
import com.example.demo.Model.DTO.AppointmentDTO;
import com.example.demo.Mapper.AppointMapper;
import com.example.demo.Model.Appointment;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.DoctorAvailability;
import com.example.demo.Model.Patient;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorAvailabilityRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Repository.PatientRepo;
import com.example.demo.Specification.AppointmentSpecification;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private AppointMapper appointMapper;
    @Autowired
    private DoctorAvailabilityRepo doctorAvailabilityRepo;

//    fetch all appointments
    public List<AppointmentDTO> getAllAppointments(){
        return appointmentRepo.findAll().stream().map(appointMapper::ToDTO).toList();
    }

//    fetch appointments details by doctor ID
    public List<AppointmentDTO> appointmentbyDoctorId(int doctorId) {
        List<Appointment> appointments = appointmentRepo.findByDoctorId(doctorId);
        if(appointments.isEmpty()){
            throw new CustomException("No such Appointment exist with this Doctor id: "+doctorId);
        }
        return appointments.stream().map(appointMapper::ToDTO).toList();
    }

//    fetch appointments details by date
    public List<AppointmentDTO> appointmentbyDate(LocalDate date) {
        List<Appointment> appointments=appointmentRepo.findByappointmentDate(date);
        if(appointments.isEmpty()){
            throw new CustomException("No such Appointment exists with this date: "+date);
        }
        return appointments.stream().map(appointMapper::ToDTO).toList();
    }

//    Book an Appointment
    public void BookTheAppointment(@Valid Appointment appointment) {
        Doctor doctor=doctorRepo.findById(appointment.getDoctor().getId())
                .orElseThrow(()->new RuntimeException("Doctor not found"));

        Patient patient=patientRepo.findById(appointment.getPatient().getId())
                .orElseThrow(()->new RuntimeException("Patient not found"));

        Appointment app=new Appointment();

        java.time.DayOfWeek day =appointment.getAppointmentDate().getDayOfWeek();
        WorkingDay workingDay=WorkingDay.valueOf(day.name());

        DoctorAvailability availability=doctorAvailabilityRepo.findByDoctorAndWorkingDay(doctor,workingDay)
                .orElseThrow(()->new RuntimeException("Doctor is not available on this Date"));

        LocalTime appointmentTime=appointment.getAppointmentTime();
        LocalTime endtime=appointmentTime.plusMinutes(30);

//        check weather the patient's appointment is already booked with the same doctor on same day.
        boolean alreadyBooked =
                appointmentRepo.existsByDoctorAndPatientAndAppointmentDate(
                        doctor,
                        patient,
                        appointment.getAppointmentDate()
                );

        if(alreadyBooked){
            throw new RuntimeException("Your appointment is already booked with "+doctor.getName()+" - "+doctor.getSpecialization()+
                    " on "+appointment.getAppointmentDate());
        }

//        check weather the patient has appointment at that time which he/she wants to book with other doctor.
        boolean alreadyBookedAtThisTime=appointmentRepo.existsByPatientAndAppointmentDateAndAppointmentTimeLessThanAndAppointmentEndTimeGreaterThan(
                patient,
                appointment.getAppointmentDate(),
                endtime,
                appointmentTime
        );

        if(alreadyBookedAtThisTime){
            throw new RuntimeException("Your Appointment is already booked with this slot with other Doctor on "
                    +appointment.getAppointmentDate()
            );
        }

        //        check the selected slot is overlapping with other slot or not.
        boolean overlap = appointmentRepo
                .existsByDoctorAndAppointmentDateAndAppointmentTimeLessThanAndAppointmentEndTimeGreaterThan(
                        doctor,
                        appointment.getAppointmentDate(),
                        endtime,
                        appointmentTime
                );

        if(overlap){
            throw new RuntimeException("This slot is overlapping with others slot!!!");
        }

        LocalTime end = getLocalTime(appointmentTime, availability);

        app.setAppointmentTime(appointmentTime);
        app.setAppointmentEndTime(end);
        app.setAppointmentDate(appointment.getAppointmentDate());
        app.setDoctor(doctor);
        app.setPatient(patient);
        app.setStatus(appointment.getStatus());
        appointmentRepo.save(app);
    }

    private static @NonNull LocalTime getLocalTime(LocalTime appointmentTime, DoctorAvailability availability) {
        LocalTime end= appointmentTime.plusMinutes(30);
        LocalTime lunchStartTime= availability.getStartTime().plusHours(4);
        LocalTime lunchEndTime= availability.getStartTime().plusHours(5);

        if(end.isBefore(appointmentTime)){
            throw new RuntimeException("appointment cannot extend into next day!!!");
        }

//        check weather the selected time slot is in between lunchtime or not.
        if(appointmentTime.isAfter(lunchStartTime) && appointmentTime.isBefore(lunchEndTime)){
            throw new RuntimeException("It's lunch Time from 1pm to 2pm, choose another slot");
        }

//       check weather the selected slot is comes under the lunchtime slot or not.
        if(end.isAfter(lunchStartTime) && end.isBefore(lunchEndTime)){
            throw new RuntimeException("Doctor would not be available between 1pm and 2pm");
        }

//       check weather the selected slot is comes under the doctor's availability time or not.
        if(appointmentTime.isBefore(availability.getStartTime()) ||  end.isAfter(availability.getEndTime())){
            throw new RuntimeException("Doctor is not available at this time, choose the time between "+availability.getStartTime()+" to "+availability.getEndTime());
        }
        return end;
    }

    //    delete the appointments details by Date
    public void deleteAppointmentByDate(LocalDate date) {
        Appointment appointment=appointmentRepo.findByAppointmentDate(date);
        if(appointment==null){
            throw new CustomException("No such appointment exists with this date"+date);
        }
        appointmentRepo.deleteAppointmentByAppointmentDate(date);
    }

//    delete the appointment by appointment_id
    public void deleteAppointmentById(Integer id) {
        appointmentRepo.findById(Long.valueOf(id)).orElseThrow(()->new CustomException("No Appointment exists with this id : "+id));
        appointmentRepo.deleteById(Long.valueOf(id));
    }

//    Specification for filtering
    public Page<AppointmentDTO> fetchAll(Pageable pageable, LocalDate appointmentDate,
                         AppointmentStatus status, LocalTime appointmentStartTime, LocalTime appointmentEndTime) {
        Specification<Appointment> spec= AppointmentSpecification.getSpecification(appointmentDate, status, appointmentStartTime, appointmentEndTime);
        List<Appointment> appointments=appointmentRepo.findAll(spec);
        if(appointments.isEmpty()){
            throw new CustomException("No such appointment exists with this criteria");
        }
        return appointmentRepo.findAll(spec, pageable).map(appointMapper::ToDTO);
    }
}
