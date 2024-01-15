package demo_ver.demo.controllers;

import demo_ver.demo.service.AuthService;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String showChangePasswordPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // Check if the user has the Admin role
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_Admin"));

        model.addAttribute("isAdmin", isAdmin);
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
