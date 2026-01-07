package com.example.demo.Controller;

import com.example.demo.Model.Users;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/reg")
    public Users register(@RequestBody Users user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user){
        return userService.verify(user);
    }

    @DeleteMapping("/del")
    public ResponseEntity<String> delUser(@RequestParam int id) {
        userService.deluser(id);
        return new ResponseEntity<>("User deleted with Id "+id, HttpStatus.OK);
    }
}
