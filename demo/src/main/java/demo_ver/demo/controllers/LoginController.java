package demo_ver.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import demo_ver.demo.service.LoginService;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String roleId,
            HttpServletRequest request
    ) {
        // Use the LoginService for login validation
        if (loginService.validateLogin(username, password, roleId)) {
            // Set a session attribute indicating successful login
            request.getSession().setAttribute("loggedInUser", username);
            // Redirect to the "viewTestCases" page upon successful login
            return "redirect:/view";
        } else {
            // Redirect back to the login page with an error message
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/changepassword")
    public String showChangePasswordPage() {
        return "ChangePassword";
    }

    @GetMapping("/view")
    public String viewTestCases() {
        return "viewTestCases";
    }
}



