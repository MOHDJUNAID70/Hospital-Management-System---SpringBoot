package com.example.demo.DTO.Patient;


import com.example.demo.DTO.User.UserSummaryDTO;
import com.example.demo.Enum.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class PatientDTO {
    private int id;
    private String name;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
    private String phone;
    private UserSummaryDTO userInfo;
}
