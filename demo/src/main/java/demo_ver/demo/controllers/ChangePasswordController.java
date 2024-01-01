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

    private final String dummyUsername = "teenesh";
    
    public ChangePasswordController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/changepassword")
    public String showChangePasswordPage() {
        return "ChangePassword";
    }

    @PostMapping("/changepassword")
    public String changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model) {

        // Validate current password
        if (!authService.validateLogin(dummyUsername, currentPassword, "Admin")) {
            model.addAttribute("error", "Incorrect current password");
            return "ChangePassword";
        }

        // Validate new password
        if (!authService.validatePassword(newPassword)) {
            model.addAttribute("error", "New password must be at least 6 characters long");
            return "ChangePassword";
        }

        // Match new password with confirm password
        if (!authService.matchPasswords(newPassword, confirmPassword)) {
            model.addAttribute("error", "New Password and Confirm Password do not match");
            return "ChangePassword";
        }

        model.addAttribute("success", "Password Changed Successfully");

        return "ChangePassword";
    }
}
