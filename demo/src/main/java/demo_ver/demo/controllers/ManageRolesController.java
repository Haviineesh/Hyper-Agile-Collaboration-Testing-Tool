package demo_ver.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import demo_ver.demo.model.ManageRole;
import demo_ver.demo.service.ManageRoleService;


@Controller
public class ManageRolesController {
    @Autowired
    private ManageRoleService manageRoleService;

    // @GetMapping("/manageroles")
    // @ResponseBody
    // public List<ManageRole> getAllRoles(){
    // return manageRoleService.getAllRoles();
    // }

    @GetMapping("/manageroles")
    public String getManageroles(Model model) {
        // List<ManageRole> roles = manageRoleService.getAllRoles();
        model.addAttribute("roles", ManageRoleService.getAllRoles());
        return "ManageRoles";
    }

    @GetMapping("/createrole")
    public String showCreateNewRole(Model model) {
        model.addAttribute("manageRole", new ManageRole());
        return "ManageRolesNew";
    }

    // @RequestMapping("/createrole")
    @PostMapping("/createrole")
    public String createRole(@ModelAttribute("manageRole") ManageRole manageRole, Model model) {
        manageRoleService.addRole(manageRole);// save product into database, using DbService
        // model.addAttribute("manageRole", manageRole);
        return "redirect:/manageroles";
    }

    @GetMapping("/editrole/{id}")
    public String editManagerole(@PathVariable("id") int id, Model model) {
        model.addAttribute("role", manageRoleService.findById(id));
        return "ManageRolesEdit";
    }

    @PostMapping("/editrole")
    public String updateManageRole(ManageRole manageRole, Model model) {
        manageRoleService.updateManageRole(manageRole);
        return "redirect:/manageroles";
    }

    @GetMapping("/deleterole/{id}")
	public String deleteRole(@PathVariable("id") int id) {
       manageRoleService.deleteRole(id);
		return "redirect:/manageroles";
	}
    
}
