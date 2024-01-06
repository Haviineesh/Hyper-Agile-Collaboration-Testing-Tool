package demo_ver.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import demo_ver.demo.service.ManageRoleService;

@Controller
public class LoginController {

    private final ManageRoleService manageRoleService;

    public LoginController(ManageRoleService manageRoleService) {
        this.manageRoleService = manageRoleService;
    }
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("roles", manageRoleService.getAllRoles());
        return "login";
    }

}
