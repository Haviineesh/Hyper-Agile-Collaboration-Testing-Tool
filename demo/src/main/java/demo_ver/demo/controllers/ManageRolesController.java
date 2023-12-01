package demo_ver.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import demo_ver.demo.model.ManageRole;
import demo_ver.demo.service.ManageRoleService;

@Controller
public class ManageRolesController {
    @Autowired
    private ManageRoleService manageRoleService;

    // @GetMapping("/manageroles")
    // @ResponseBody
    // public List<ManageRole> getAllRoles(){
    //     return manageRoleService.getAllRoles();
    // }

    @GetMapping("/manageroles")
    public String manageroles(Model model) {
        List<ManageRole> roles = manageRoleService.getAllRoles();
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
