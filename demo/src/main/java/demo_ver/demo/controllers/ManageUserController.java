package demo_ver.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String showAddUserPage() {
        return "ManageUserAdd";
    }
}

