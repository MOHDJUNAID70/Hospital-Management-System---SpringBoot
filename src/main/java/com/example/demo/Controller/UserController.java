package com.example.demo.Controller;

import com.example.demo.Model.DTO.UserRegistrationDTO;
import com.example.demo.Model.Users;
import com.example.demo.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
@Tag(name = "User APIs")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserRegistrationDTO userRegistrationDTO) {
        userService.register(userRegistrationDTO);
        return "redirect:/logins";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user, HttpSession session){
        String result =userService.verify(user);
        if(result.equals("Login Successful")){
            session.setAttribute("user", user.getUsername());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/get-user")
    public ResponseEntity<Users> getUser(@RequestParam("id") int id){
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @DeleteMapping("/del")
    public void delUser(@RequestParam int id) {
        userService.deluser(id);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>("Logout Successful", HttpStatus.OK);
    }
}
