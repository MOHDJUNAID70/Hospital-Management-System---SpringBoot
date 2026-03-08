package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/")
    public String index() {
        return "layouts/index";
    }

    @GetMapping("/admin/dashboard")
    public String specialists() {
        return "dashboard/admin";
    }

    @GetMapping("/doctor/dashboard")
    public String doctor() {
        return "dashboard/doctor";
    }

    @GetMapping("/patient/dashboard")
    public String patients() {
        return "dashboard/patient";
    }

    @GetMapping("/logins")
    public String loginPage() {
        return "auth/loginPage";
    }

    @GetMapping("/reg")
    public String registerPage() {
        return "auth/register";
    }
}
