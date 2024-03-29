package demo_ver.demo.controllers;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @GetMapping("/home")
    public String showHomePage(Model model, Authentication authentication,
            @AuthenticationPrincipal UserDetails userDetails) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // Check if the user has the Admin role
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_Admin"));

        model.addAttribute("isAdmin", isAdmin);
        return "HomePage";
    }

}
