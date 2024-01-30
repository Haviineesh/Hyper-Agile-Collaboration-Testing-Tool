package demo_ver.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/roleadd")
    public ResponseEntity<String> addRole(@RequestBody Map<String, Object> requestBody) {
        // Extract necessary fields from the request body
        String roleName = (String) requestBody.get("roleName");
        String description = (String) requestBody.get("description");

        // Check if the role already exists
        if (fakeRoleList.stream().anyMatch(role -> role.getRoleName().equals(roleName))) {
            // Role with the same roleName already exists
            return ResponseEntity.badRequest().body("Role with roleName " + roleName + " already exists.");
        }

        // If the roleList is empty, set the roleID to 1000
        int roleID = fakeRoleList.isEmpty() ? 1000 : fakeRoleList.get(fakeRoleList.size() - 1).getRoleID() + 1;

        // Create a new ManageRole instance
        ManageRole newRole = new ManageRole(roleID, roleName, description);

        // Add the new role to the list
        fakeRoleList.add(newRole);

        // Return success response
        return ResponseEntity.ok("Role added successfully.");
    }

}
