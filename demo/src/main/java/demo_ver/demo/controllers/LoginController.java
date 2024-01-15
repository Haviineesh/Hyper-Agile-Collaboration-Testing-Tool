package demo_ver.demo.controllers;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// import demo_ver.demo.service.ManageRoleService;

@Controller
public class LoginController {

    // private final ManageRoleService manageRoleService;

    // public LoginController(ManageRoleService manageRoleService) {
    // this.manageRoleService = manageRoleService;
    // }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        // model.addAttribute("roles", manageRoleService.getAllRoles());
        return "login";
    }

    @GetMapping("/home")
    public String showHomePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // Check if the user has the Admin role
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_Admin"));

        model.addAttribute("isAdmin", isAdmin);
        return "HomePage";
    }

}
