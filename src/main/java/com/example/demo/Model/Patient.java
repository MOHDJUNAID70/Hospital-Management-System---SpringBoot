package com.example.demo.Model;

import com.example.demo.Enum.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must only contain alphabets and space")
    private String name;
    @Column(nullable = false)
    @NotNull
    @Min(1)
    @Max(150)
    private Integer age;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotBlank
    @Size(min = 5, max = 150)
    private String address;
    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must have 10 digits")
    private String phone;
}
