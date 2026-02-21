package com.example.demo.Users;

import com.example.demo.ExceptionHandler.CustomException;
import com.example.demo.Model.Users;
import com.example.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CurrentUser {

    @Autowired
    private UserRepo userRepo;

    public Users getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        String username = ((UserDetails) Objects.requireNonNull(auth.getPrincipal())).getUsername();
        Users user = userRepo.findByUsername(username);
        if(user == null){
            throw new CustomException("Invalid username");
        }
        return user;
    }
}
