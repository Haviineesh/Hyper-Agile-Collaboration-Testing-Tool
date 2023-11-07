package demo_ver.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class ManageRolesController {

    @GetMapping("/manageroles")
    public String manageroles() {
        return "pages/ManageRoles";
    }
    
}
