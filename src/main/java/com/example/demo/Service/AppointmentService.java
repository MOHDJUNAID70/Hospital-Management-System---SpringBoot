package com.example.demo.Service;

import com.example.demo.Enum.AppointmentStatus;
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
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
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
        return appointmentRepo.findByDoctorId(doctorId).stream().map(appointMapper::ToDTO).toList();
    }

//    fetch appointments details by date
    public List<AppointmentDTO> appointmentbyDate(LocalDate date) {
        return appointmentRepo.findByappointmentDate(date).stream().map(appointMapper::ToDTO).toList();
    }

//    Book an Appointment
    public void BookTheAppointment(@Valid Appointment appointment) {
        Doctor doctor=doctorRepo.findById(appointment.getDoctor().getId())
                .orElseThrow(()->new RuntimeException("Doctor not found"));

        Patient patient=patientRepo.findById(appointment.getPatient().getId())
                .orElseThrow(()->new RuntimeException("Patient not found"));

        Appointment app=new Appointment();

        DayOfWeek dayOfWeek=appointment.getAppointmentDate().getDayOfWeek();

        DoctorAvailability availability=doctorAvailabilityRepo.findByDoctorAndDayOfWeek(doctor,dayOfWeek)
                .orElseThrow(()->new RuntimeException("Doctor is not available on this Date"));

        LocalTime appointmentTime=appointment.getAppointmentTime();
        LocalTime end=appointmentTime.plusMinutes(30);
        LocalTime lunchStartTime=availability.getStartTime().plusHours(4);
        LocalTime lunchEndTime=availability.getStartTime().plusHours(5);

//        check weather the selected time slot is in between lunchtime or not.
        if(appointmentTime.isAfter(lunchStartTime) && appointmentTime.isBefore(lunchEndTime)){
            throw new RuntimeException("It's lunch Time from 1pm to 2pm, choose another slot");
        }

//       check weather the selected slot is comes under the unch time slot or not.
        if(end.isAfter(lunchStartTime) && end.isBefore(lunchEndTime)){
            throw new RuntimeException("Doctor would not be available between 1pm and 2pm");
        }

//       check weather the selected slot is comes under the doctor's availability time or not.
        if(appointmentTime.isBefore(availability.getStartTime()) ||  end.isAfter(availability.getEndTime())){
            throw new RuntimeException("Doctor is not available at this time");
        }

//        check the selected slot is overlapping with other slot or not.
        boolean overlap = appointmentRepo
                        .existsByDoctorAndAppointmentDateAndAppointmentTimeLessThanAndAppointmentEndTimeGreaterThan(
                                doctor,
                                appointment.getAppointmentDate(),
                                end,
                                appointmentTime
                        );

        if(overlap){
            throw new RuntimeException("This slot is overlapping with others slot!!!");
        }

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
                end,
                appointmentTime
        );

        if(alreadyBookedAtThisTime){
            throw new RuntimeException("Your Appointment is already booked with this slot with other Doctor on "
                    +appointment.getAppointmentDate()
            );
        }

        app.setAppointmentTime(appointmentTime);
        app.setAppointmentEndTime(end);
        app.setAppointmentDate(appointment.getAppointmentDate());
        app.setDoctor(doctor);
        app.setPatient(patient);
        app.setStatus(appointment.getStatus());
        appointmentRepo.save(app);
    }

//    delete the appointments details by Date
    public void deleteAppointmentByDate(LocalDate date) {
        appointmentRepo.deleteAppointmentByAppointmentDate(date);
    }

//    delete the appointment by appointment_id
    public void deleteAppointmentById(Integer id) {
        appointmentRepo.deleteById(Long.valueOf(id));
    }
}
