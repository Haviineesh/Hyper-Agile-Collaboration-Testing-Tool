package demo_ver.demo.controllers;

import demo_ver.demo.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChangePasswordController {

    private final AuthService authService;
    
    public ChangePasswordController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/changepassword")
    public String showChangePasswordPage() {
        return "ChangePassword";
    }

}
