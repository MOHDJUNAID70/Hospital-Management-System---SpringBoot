package com.example.demo.Mapper;

import com.example.demo.DTO.User.UserSummaryDTO;
import com.example.demo.Model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserSummaryMapper {
    @Mapping(source = "userId", target = "userId")
    UserSummaryDTO ToDTO(Users user);
}
