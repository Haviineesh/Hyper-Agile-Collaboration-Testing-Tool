package demo_ver.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        String body = "Click the following link to reset your password: http://HyperAgileCollaborationTestingTool/reset-password?token="
                + resetToken;
        emailService.sendPasswordResetEmail(userEmail, subject, body);

        // For demonstration purposes, let's assume the link is sent successfully
        model.addAttribute("successMessage", "Password reset link sent to your email");

        return "ForgotPassword";
    }

    private String generateResetToken(String email) {
        // Add your logic to generate a reset token
        // This can involve generating a unique token, saving it in the database, etc.
        return null;
    }
}
