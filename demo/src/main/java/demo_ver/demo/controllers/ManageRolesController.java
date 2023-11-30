package demo_ver.demo.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import demo_ver.demo.model.ManageRole;
import demo_ver.demo.service.ManageRoleService;

@Controller
public class ManageRolesController {

    @GetMapping("/manageroles")
    public String manageroles(Model model) {
        List<ManageRole> roles = ManageRoleService.getAllRoles();
		model.addAttribute("roles", roles);
        return "ManageRoles";
    }

    @GetMapping("/rolestestcases")
    public String rolestestcases() {
        return "RolesTestCases";
    }

    @GetMapping("/editrole")
    public String managerolesedit(){
        return "ManageRolesEdit";
    }

    
}
