package demo_ver.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo_ver.demo.model.ManageRole;

@RestController
@RequestMapping("/api")
public class FakeController {

    private final List<ManageRole> fakeRoleList;

    public FakeController() {
        // Initialize the fake role list
        fakeRoleList = new ArrayList<>();
        fakeRoleList.add(new ManageRole(1000, "ROLE_Admin", "Fake Administration"));
        fakeRoleList.add(new ManageRole(1002, "ROLE_Tester", "Fake unit tester"));
        fakeRoleList.add(new ManageRole(1003, "ROLE_Manager", "Fake manager"));
        fakeRoleList.add(new ManageRole(1003, "ROLE_Developer", "Programming"));

    }

    @GetMapping("/rolegetallroles")
    public List<ManageRole> getAllRoles() {
        // Create and return a fake list
        return fakeRoleList;
    }

   
}
