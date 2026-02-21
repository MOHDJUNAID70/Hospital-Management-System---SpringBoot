package com.example.demo.Controller;

import com.example.demo.Model.DTO.UserRegistrationDTO;
import com.example.demo.Model.Users;
import com.example.demo.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@Tag(name = "User APIs")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/reg")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.register(userRegistrationDTO);
        return new ResponseEntity<>("Registration Successful",HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user){
        return userService.verify(user);
    }

    @GetMapping("/get-user")
    public ResponseEntity<Users> getUser(@RequestParam("id") int id){
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @DeleteMapping("/del")
    public ResponseEntity<String> delUser(@RequestParam int id) {
        userService.deluser(id);
        return new ResponseEntity<>("User deleted with Id "+id, HttpStatus.OK);
    }
}
