package demo_ver.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import demo_ver.demo.model.ManageUser;
import demo_ver.demo.service.ManageUserService;

@Controller
public class ManageUserController {

    @Autowired
    private ManageUserService manageUserService;

    // @GetMapping("/manageuser")
    // @ResponseBody
    // public List<ManageUser> getAllUsers(){
    //     return manageUserService.getAllUsers();
    // }

    @GetMapping("/manageuser")
    public String manageusers(Model model) {
        List<ManageUser> users = manageUserService.getAllUsers();
		model.addAttribute("users", users);
        return "ManageUser";
    }

    @GetMapping("/adduser")
    public String showAddUserPage(Model model) {
        model.addAttribute("manageUser", new ManageUser());
        return "ManageUserAdd";
    }

    public void setManageUserService(ManageUserService manageUserService) {
        this.manageUserService = manageUserService;
    }

    // @PostMapping("/adduser")
    // public String adduser(@ModelAttribute("manageUser") ManageUser manageUser, Model model) {
    //     manageUserService.addUser(manageUser);// save puser into database, using DbService
    //    model.addAttribute("manageUser", manageUser);
    //     return "ManageUser";
    // }

    @PostMapping("/adduser")
    public String adduser(@ModelAttribute("manageUser") ManageUser manageUser, Model model) {
        // Check if the username already exists
        if (manageUserService.isUsernameExists(manageUser.getUsername())) {
            model.addAttribute("usernameExists", true);
            return "ManageUserAdd";
        }

        if (manageUserService.isEmailExists(manageUser.getEmail())) {
            model.addAttribute("emailExists", true);
            return "ManageUserAdd";
        }

        // If the username does not exist, save the user
        manageUserService.addUser(manageUser);
        model.addAttribute("manageUser", manageUser);
        return "ManageUser";
    }

}

