package demo_ver.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

public class LoginController {
    
    @GetMapping("/login")
    public String login() {
        return "login"; // This will look for a template named "login.html"
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // This will look for a template named "register.html"
    }
}
