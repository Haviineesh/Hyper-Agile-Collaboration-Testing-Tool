package demo_ver.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import demo_ver.demo.service.EmailService;

@Controller
public class ForgotPasswordController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/forgotpassword")
    public String showForgotPasswordPage() {
        return "ForgotPassword";
    }

    @PostMapping("/forgotpassword")
    public String handleForgotPasswordForm(@RequestParam String userEmail, Model model) {
        // Logic to generate a password reset token and save it in the database
        String resetToken = generateResetToken(userEmail);

        // Send the password reset email
        String subject = "Password Reset";
        String body = "Click the following link to reset your password: http://localhost:8090/resetpassword?token="
                + resetToken;
        emailService.sendPasswordResetEmail(userEmail, subject, body);

        // For demonstration purposes, let's assume the link is sent successfully
        model.addAttribute("successMessage", "Password reset link sent to your email");

        return "ForgotPassword";
    }

    @GetMapping("/resetpassword")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        // Validate the token (you need to implement this logic)
        if (isValidToken(token)) {
            model.addAttribute("resetToken", token);
            return "ResetPassword";
        } else {
            model.addAttribute("errorMessage", "Invalid or expired token");
            return "ResetPassword";
        }
    }

    private boolean isValidToken(String token) {
        // Implement the logic to validate the token (e.g., check against stored tokens,
        // expiration time)
        // Return true if the token is valid, false otherwise
        return true;
    }

    @PostMapping("/resetpassword")
    public String handleResetPasswordForm(@RequestParam String token, @RequestParam String newPassword, Model model) {
        if (isValidToken(token)) {
            if (isPasswordValid(newPassword)) {
                // Implement logic to update user's password with the new password
                // For demonstration purposes, assume success
                model.addAttribute("successMessage", "Password reset successful");
                return "ResetPassword";
            } else {
                model.addAttribute("errorMessage", "Password should be at least 6 characters");
                return "ResetPassword";
            }
        } else {
            model.addAttribute("errorMessage", "Invalid or expired token");
            return "ResetPassword";
        }
    }

    private boolean isPasswordValid(String password) {
        // Add your password validation logic here
        return password.length() >= 6;
    }

    private String generateResetToken(String email) {
        // Generate a secure token
        return UUID.randomUUID().toString();
    }
}
