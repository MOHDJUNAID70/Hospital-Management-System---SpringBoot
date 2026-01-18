package com.example.demo.Service;

import com.example.demo.Enum.Role;
import com.example.demo.JWT.JWTService;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Model.DTO.UserInfoDTO;
import com.example.demo.Model.DTO.UserRegistrationDTO;
import com.example.demo.Model.Users;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Specification.UserSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService service;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    public void register(UserRegistrationDTO userRegistrationDTO) {
        String rawPassword = userRegistrationDTO.getPassword();
        String encodedPassword = encoder.encode(rawPassword);
        Users user = new Users();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(userRegistrationDTO.getRole());
        userRepo.save(user);
    }

    public void deluser(int id) {
        userRepo.deleteById(id);
    }

    public String verify(Users user) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(auth.isAuthenticated()) {
            return service.generateToken(user.getUsername());
        }
        return "fail";
    }

    public Page<UserInfoDTO> fetchAll(Pageable pageable, String username, String email, Role role) {
        Specification<Users> specification=UserSpecification.getSpecification(username,email,role);
        return userRepo.findAll(specification, pageable).map(userMapper::ToDTO);
    }
}
