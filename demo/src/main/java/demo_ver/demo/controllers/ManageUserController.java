package demo_ver.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManageUserController {

    @GetMapping("/manageuser")
    public String showManageUserPage() {
        return "ManageUser";
    }

    @GetMapping("/adduser")
    public String showAddUserPage() {
        return "ManageUserAdd";
    }
}

