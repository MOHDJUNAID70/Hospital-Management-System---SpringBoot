package com.example.demo.Model;

import com.example.demo.Enum.WorkingDay;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id",  nullable = false)
    private Doctor doctor;
    @NotNull
    @Enumerated(EnumType.STRING)
    private WorkingDay workingDay;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
}
