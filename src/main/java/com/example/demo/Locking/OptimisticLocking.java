package com.example.demo.Locking;


import com.example.demo.Model.Appointment;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.Patient;
import com.example.demo.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class OptimisticLocking {
    @Autowired
    private AppointmentService appointmentService;

    public void TestOptimisticLocking() throws InterruptedException {
        Thread th1=new Thread(()->{
            try {
                Appointment ap1 = new Appointment();
                Doctor doc1 = new Doctor();
                doc1.setId(305);
                Patient p1 = new Patient();
                p1.setId(502);
                ap1.setDoctor(doc1);
                ap1.setPatient(p1);
                ap1.setAppointmentDate(LocalDate.of(2026,2,5));
                ap1.setAppointmentTime(LocalTime.of(15,25,15));
                System.out.println("thread1 is trying to book the appointment");
                appointmentService.BookTheAppointment(ap1);
                System.out.println("Thread1 has been successfully booked");
            }catch (DataIntegrityViolationException e){
                throw new DataIntegrityViolationException(e.getMessage());
            }
        });

        Thread th2=new Thread(()->{
            try {
                Appointment ap2 = new Appointment();
                Doctor doc2 = new Doctor();
                doc2.setId(305);
                Patient p2 = new Patient();
                p2.setId(402);
                ap2.setDoctor(doc2);
                ap2.setPatient(p2);
                ap2.setAppointmentDate(LocalDate.of(2026,2,5));
                ap2.setAppointmentTime(LocalTime.of(15, 25, 16));
                System.out.println("thread2 is trying to book the appointment");
                appointmentService.BookTheAppointment(ap2);
                System.out.println("Thread2 has been successfully booked");
            }catch (DataIntegrityViolationException e){
                throw new DataIntegrityViolationException(e.getMessage());
            }
        });
        th1.start();
        th2.start();
        th1.join();
        th2.join();
    }
}
