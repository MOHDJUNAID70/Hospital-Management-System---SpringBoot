package com.example.demo.DTO.User;

import com.example.demo.Enum.Role;
import lombok.Data;

@Data
public class UserInfoDTO {
    private int id;
    private String username;
    private String email;
    private Role role;
}
