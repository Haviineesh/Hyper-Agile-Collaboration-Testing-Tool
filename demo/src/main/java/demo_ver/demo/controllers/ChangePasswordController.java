package demo_ver.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChangePasswordController {

    @GetMapping("/ChangePassword")
    public String showChangePasswordPage() {
        return "ChangePassword";
    }
}