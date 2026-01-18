package com.example.demo.Mapper;

import com.example.demo.Model.DTO.UserInfoDTO;
import com.example.demo.Model.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserInfoDTO ToDTO(Users user);
}
