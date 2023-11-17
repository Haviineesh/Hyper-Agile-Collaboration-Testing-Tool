package demo_ver.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManageRolesController {

    @GetMapping("/manageroles")
    public String manageroles() {
        return "ManageRoles";
    }

    @GetMapping("/rolestestcases")
    public String rolestestcases() {
        return "RolesTestCases";
    }

    
}
