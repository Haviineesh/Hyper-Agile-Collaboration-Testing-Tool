package demo_ver.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChangePasswordController {

    @GetMapping("/change-password")
    public String showChangePasswordPage() {
        return "change-password";
    }
}