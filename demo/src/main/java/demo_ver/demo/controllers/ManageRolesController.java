package demo_ver.demo.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String getManageroles(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // Check if the user has the Admin role
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_Admin"));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("roles", manageRoleService.apiGetAllRoles());
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
        manageRoleService.apiAddRole(manageRole);// save product into database,
        // model.addAttribute("manageRole", manageRole);
        return "redirect:/manageroles";
    }

    @GetMapping("/editrole/{id}")
    public String editManagerole(@PathVariable("id") int id, Model model) {
        model.addAttribute("role", manageRoleService.apiFindById(id));
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
