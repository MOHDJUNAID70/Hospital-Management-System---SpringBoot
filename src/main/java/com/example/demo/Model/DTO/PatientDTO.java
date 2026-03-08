package com.example.demo.Model.DTO;


import com.example.demo.Enum.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class PatientDTO {
    private int id;
    private String name;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
    private String phone;
}
