package com.example.demo.Model.DTO;

import com.example.demo.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    @NotBlank(message = "Username can't be Blank")
    @Size(min = 3, max = 50, message = "username's length should be between 3 to 50")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username must have Alphabets and Numbers only")
    private String username;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Format!!!")
    private String email;
    @NotBlank(message = "Password must not be blank!!!")
    @Size(min = 8, max = 50, message = "Password length is less than 8")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,50}$",
            message = "Password must include 1 uppercase, 1 lowercase, 1 digit, and 1 special character"
    )
    private String password;
    @NotNull(message = "Role is Required")
    @Enumerated(EnumType.STRING)
    private Role role;
}
