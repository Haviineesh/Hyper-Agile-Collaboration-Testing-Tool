// package demo_ver.demo.controllers;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;
// import java.util.UUID;

// import demo_ver.demo.model.ManageUser;
// import demo_ver.demo.service.EmailService;
// import demo_ver.demo.service.ManageUserService;

// @Controller
// public class ForgotPasswordController {

//     @Autowired
//     private ManageUserService manageUserService;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     @Autowired
//     private EmailService emailService;

//     @GetMapping("/forgotpassword")
//     public String showForgotPasswordPage() {
//         return "ForgotPassword";
//     }

//     @PostMapping("/forgotpassword")
//     public String handleForgotPasswordForm(@RequestParam String userEmail, Model model) {
//         // Logic to generate a password reset token and save it in the database
//         String resetToken = generateResetToken(userEmail);

//         // Find the user by email and set the reset token
//         ManageUser user = manageUserService.getUserByEmail(userEmail);
//         if (user != null) {
//             manageUserService.updateResetToken(user, resetToken);
//         }

//         // Send the password reset email
//         String subject = "Password Reset";
//         String body = "Click the following link to reset your password: http://localhost:8090/resetpassword?token="
//                 + resetToken;
//         emailService.sendPasswordResetEmail(userEmail, subject, body);

//         // For demonstration purposes, let's assume the link is sent successfully
//         model.addAttribute("successMessage", "Password reset link sent to your email");

//         return "ForgotPassword";
//     }

//     @GetMapping("/resetpassword")
//     public String showResetPasswordForm(@RequestParam String token, Model model) {
//         // Validate the token (you need to implement this logic)
//         if (isValidToken(token)) {
//             model.addAttribute("resetToken", token);
//             return "ResetPassword";
//         } else {
//             model.addAttribute("errorMessage", "Invalid or expired token");
//             return "ResetPassword";
//         }
//     }

//     private boolean isValidToken(String token) {
//         // Implement the logic to validate the token (e.g., check against stored tokens,
//         // expiration time)
//         // Return true if the token is valid, false otherwise
//         return true;
//     }

//     @PostMapping("/resetpassword")
//     public String handleResetPasswordForm(@RequestParam String token, @RequestParam String oldPassword,
//             @RequestParam String newPassword, Model model) {
//         // Validate the token (you need to implement this logic)
//         if (!isValidToken(token)) {
//             model.addAttribute("errorMessage", "Invalid or expired token");
//             return "ResetPassword";
//         }

//         // Find the user by token
//         ManageUser user = findUserByToken(token);
//         if (user == null) {
//             model.addAttribute("errorMessage", "User not found");
//             return "ResetPassword";
//         }

//         // Verify the old password
//         if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
//             model.addAttribute("errorMessage", "Incorrect old password");
//             return "ResetPassword";
//         }

//         // Update user's password with the new password
//         user.setPassword(passwordEncoder.encode(newPassword));
//         // Update the password in the user service or repository
//         manageUserService.updateUserPassword(user);

//         // For demonstration purposes, assume success
//         model.addAttribute("successMessage", "Password reset successful");
//         return "ResetPassword";
//     }

//     private ManageUser findUserByToken(String token) {
//         return manageUserService.findUserByResetToken(token);
//     }

//     private boolean isPasswordValid(String password) {
//         // Add your password validation logic here
//         return password.length() >= 6;
//     }

//     private String generateResetToken(String email) {
//         // Generate a secure token
//         return UUID.randomUUID().toString();
//     }
// }
package demo_ver.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import demo_ver.demo.model.ManageUser;
import demo_ver.demo.service.EmailService;
import demo_ver.demo.service.ManageUserService;

@Controller
public class ForgotPasswordController {

    @Autowired
    private ManageUserService manageUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @GetMapping("/forgotpassword")
    public String showForgotPasswordPage() {
        return "ForgotPassword";
    }

    @PostMapping("/forgotpassword")
    public String handleForgotPasswordForm(@RequestParam String userEmail, Model model) {
        // Find the user by email
        ManageUser user = manageUserService.getUserByEmail(userEmail);

        if (user == null) {
            // User not found, show error message
            model.addAttribute("errorMessage", "Invalid email address");
            return "ForgotPassword";
        }

        // Logic to generate a password reset token and save it in the database
        String resetToken = manageUserService.generateResetToken(user.getEmail());

        // Set the reset token for the user
        manageUserService.updateResetToken(user, resetToken);

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
        // Validate the token
        if (!manageUserService.isValidToken(token)) {
            model.addAttribute("errorMessage", "Invalid or expired token");
            return "ResetPassword";
        }

        model.addAttribute("resetToken", token);
        return "ResetPassword";
    }

    @PostMapping("/resetpassword")
    public String handleResetPasswordForm(@RequestParam String token, @RequestParam String newPassword, Model model) {
        // Validate the token
        if (!manageUserService.isValidToken(token)) {
            model.addAttribute("errorMessage", "Invalid or expired token");
            return "ResetPassword";
        }

        // Find the user by token
        ManageUser user = manageUserService.findUserByResetToken(token);
        if (user == null) {
            model.addAttribute("errorMessage", "User not found");
            return "ResetPassword";
        }

        // Validate the new password
        if (!manageUserService.isPasswordValid(newPassword)) {
            model.addAttribute("errorMessage", "Password should be at least 6 characters");
            return "ResetPassword";
        }

        // Update user's password with the new password
        manageUserService.updateUserPassword(user, newPassword);

        // For demonstration purposes, assume success
        model.addAttribute("successMessage", "Password reset successful");
        return "ResetPassword";
    }
}
