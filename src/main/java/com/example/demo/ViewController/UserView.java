package com.example.demo.ViewController;

import com.example.demo.Model.DTO.UserRegistrationDTO;
import com.example.demo.Model.Users;
import com.example.demo.Service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserView {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserRegistrationDTO userRegistrationDTO, org.springframework.validation.BindingResult bindingResult, org.springframework.ui.Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.userRegistrationDTO", bindingResult);
            model.addAttribute("userRegistrationDTO", userRegistrationDTO);
            return "auth/register";
        }
        userService.register(userRegistrationDTO);
        return "redirect:/logins?success";
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

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>("Logout Successful", HttpStatus.OK);
    }
}
