package demo_ver.demo.controllers;

import javax.validation.Valid;

// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import demo_ver.demo.model.User;
import demo_ver.demo.service.AuthService;


@Controller
public class LoginController {

   
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/forgotpassword")
    public String showForgotPasswordPage() {
        return "ForgotPassword";
    }

    // @PostMapping("/login")
    // public ResponseEntity<String> login(@RequestBody User loginUser) {
    //     String username = loginUser.getUsername();
    //     String password = loginUser.getPassword();
    //     String roleId = loginUser.getRoleId();

    //     if (loginService.validateLogin(username, password, roleId)) {

    //         return ResponseEntity.ok("Login Successful!");
    //     } else {
    //         return ResponseEntity.status(401).body("Invalid username or password or roleId");
    //     }
    // }

    // @PostMapping("/login")
    // public ResponseEntity<String> login(@ModelAttribute User loginUser) {
    //     String username = loginUser.getUsername();
    //     String password = loginUser.getPassword();
    //     String role = loginUser.getRole();

    //     if (authService.validateLogin(username, password, role)) {
    //         return ResponseEntity.ok("Login Successful!");
    //     } else {
    //         return ResponseEntity.status(401).body("Invalid username or password or role");
    //     }
    // }

    // @PostMapping("/login")
    // public String login(@RequestParam String username, @RequestParam String password, Model model) {
    //     // Custom logic for checking login credentials
    //     if (authService.validateLogin(username, password, "Admin")) {
    //         return "redirect:/manageuser";
    //     } else {
    //         model.addAttribute("error", "Invalid username or password or role");
    //         return "login";
    //     }
    // }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute User loginUser, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
        model.addAttribute("error", "Invalid username or password or role");
        return "login";
    }

    // Custom logic for checking login credentials
    if (authService.validateLogin(loginUser.getUsername(), loginUser.getPassword(), loginUser.getRole())) {
        return "redirect:/manageuser";
    } else {
        model.addAttribute("error", "Invalid username or password or role");
        return "login";
    }
}

    @PostMapping("/forgotpassword")
    public String handleForgotPasswordForm(@RequestParam String email, Model model) {

        // For demonstration purposes, let's assume the link is sent successfully
        model.addAttribute("successMessage", "Password reset link sent to your email");

        return "ForgotPassword";
    }

}



