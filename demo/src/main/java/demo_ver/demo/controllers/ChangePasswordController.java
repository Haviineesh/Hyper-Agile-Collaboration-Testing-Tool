package demo_ver.demo.controllers;

import demo_ver.demo.service.AuthService;

import java.security.Principal;

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

    @PostMapping("/changepassword")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model, Principal principal) {

        String username = principal.getName();

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New password and confirm password do not match.");
            return "ChangePassword";
        }

        if (newPassword.length() < 6) {
            model.addAttribute("error", "New password should be at least 6 characters.");
            return "ChangePassword";
        }

        if (authService.changePassword(username, currentPassword, newPassword)) {
            model.addAttribute("success", "Password changed successfully.");
        } else {
            model.addAttribute("error", "Incorrect current password. Please try again.");
        }

        return "ChangePassword";
    }

}
