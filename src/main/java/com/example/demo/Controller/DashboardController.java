package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class DashboardController {

    @GetMapping("/home")
    public String home() {
        LocalDateTime now=LocalDateTime.now();
        System.out.println("now: "+now);
        return "Welcome to the Hospital Management Dashboard";
    }
}
