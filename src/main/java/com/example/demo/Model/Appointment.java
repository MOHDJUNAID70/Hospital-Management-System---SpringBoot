package com.example.demo.Model;

import com.example.demo.Enum.AppointmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JoinColumn(name="appointmentid")
    private Integer appointmentid;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @NotNull
    private LocalDate appointmentDate;
    @NotNull
    private LocalTime appointmentTime;
    private LocalTime appointmentEndTime;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @PrePersist
    public void prePersist(){
        status=AppointmentStatus.Booked;
    }
}
