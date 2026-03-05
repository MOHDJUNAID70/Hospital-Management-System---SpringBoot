package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/")
    public String index() {
        return "layouts/index";
    }

    @GetMapping("/specialists")
    public String specialists() {
        return "admin";
    }

    @GetMapping("/doctor")
    public String doctor() {
        return "doctor";
    }

    @GetMapping("/patients")
    public String patients() {
        return "patient";
    }

    @GetMapping("/logins")
    public String loginPage() {
        return "auth/loginPage";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }
}
