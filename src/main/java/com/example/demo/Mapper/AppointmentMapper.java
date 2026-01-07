//package com.example.demo.Mapper;
//
//
//import com.example.demo.DTO.AppointmentDTO;
//import com.example.demo.DTO.DoctorDTO;
//import com.example.demo.DTO.PatientDTO;
//import com.example.demo.Model.Appointment;
//import com.example.demo.Model.Doctor;
//import com.example.demo.Model.Patient;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AppointmentMapper {
//
//    // manual mapping
//    public AppointmentDTO ToDTO(Appointment appointment) {
//        AppointmentDTO dto = new AppointmentDTO();
//        dto.setId(appointment.getId());
//        dto.setAppointmentDate(appointment.getAppointmentDate());
//        dto.setAppointmentTime(appointment.getAppointmentTime());
//        dto.setStatus(appointment.getStatus());
//
//        Doctor doctor = appointment.getDoctor();
//        DoctorDTO doctorDTO = new DoctorDTO();
//        doctorDTO.setId(doctor.getId());
//        doctorDTO.setDoctorName(doctor.getName());
//        doctorDTO.setSpecialization( doctor.getSpecialization());
//
//        dto.setDoctorDTO(doctorDTO);
//
//        Patient pateint= appointment.getPatient();
//        PatientDTO patientDTO = new PatientDTO();
//        patientDTO.setId(pateint.getId());
//        patientDTO.setName(pateint.getName());
//        patientDTO.setAge(pateint.getAge());
//
//        dto.setPatientDTO(patientDTO);
//        return dto;
//    }
//}
