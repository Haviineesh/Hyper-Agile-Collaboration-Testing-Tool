package demo_ver.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo_ver.demo.model.ManageUser;
import demo_ver.demo.service.ManageRoleService;
import demo_ver.demo.service.ManageUserService;

@Controller
public class ManageUserController {

    @Autowired
    private ManageUserService manageUserService;

    // @GetMapping("/manageuser")
    // @ResponseBody
    // public List<ManageUser> getAllUsers(){
    // return manageUserService.getAllUsers();
    // }

    @GetMapping("/manageuser")
    public String manageusers(Model model) {
        // List<ManageUser> users = manageUserService.getAllUsers();
        model.addAttribute("users", ManageUserService.getAllUsers());
        return "ManageUser";
    }

    @GetMapping("/adduser")
    public String showAddUserPage(Model model) {
        model.addAttribute("manageUser", new ManageUser());
        model.addAttribute("roles", ManageRoleService.getAllRoles());
        return "ManageUserAdd";
    }

    // @RequestMapping("/adduser")
    @PostMapping("/adduser")
    public String adduser(@ModelAttribute("manageUser") ManageUser manageUser, @RequestParam("role") int roleID,
            Model model) {
        if (manageUserService.isUsernameExists(manageUser.getUsername())) {
            model.addAttribute("usernameExists", true);
            return "ManageUserAdd";
        }

        if (manageUserService.isEmailExists(manageUser.getEmail())) {
            model.addAttribute("emailExists", true);
            return "ManageUserAdd";
        }

        manageUserService.addUser(manageUser, roleID);

        // model.addAttribute("manageUser", manageUser);

        return "redirect:/manageuser";
    }

    @GetMapping("/deleteuser/{userID}")
    public String deleteUser(@PathVariable("userID") int userID) {
        manageUserService.deleteUser(userID);
        return "redirect:/manageuser";
    }

    @GetMapping("/edituser/{userID}")
    public String showEditUserForm(@PathVariable("userID") int userID, Model model) {
        ManageUser userToEdit = manageUserService.getUserById(userID);
        model.addAttribute("manageUser", userToEdit);
        model.addAttribute("roles", ManageRoleService.getAllRoles());
        return "ManageUserEdit"; //
    }

    @PostMapping("/updateuser")
    public String updateUser(@ModelAttribute("manageUser") ManageUser manageUser, @RequestParam("roleID") int roleID) {
        manageUserService.updateUser(manageUser, roleID);
        return "redirect:/manageuser";
    }

}
