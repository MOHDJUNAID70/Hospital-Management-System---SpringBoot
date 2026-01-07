package com.example.demo.Model;

import com.example.demo.Enum.DoctorSpecializations;
import com.example.demo.Enum.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must have only alphabets and space")
    private String name;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "experience_in_years", nullable = false)
    @NotNull
    @Min(1)
    @Max(50)
    private Integer experienceInYears;
    @NotNull
    @Enumerated(EnumType.STRING)
    private DoctorSpecializations specialization;
}
