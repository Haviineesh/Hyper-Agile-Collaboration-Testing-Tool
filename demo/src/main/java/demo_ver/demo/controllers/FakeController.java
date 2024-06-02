package demo_ver.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        fakeRoleList.add(new ManageRole(1001, "ROLE_Tester", "Fake unit tester"));
        fakeRoleList.add(new ManageRole(1002, "ROLE_Product Manager", "Fake manager"));
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
        String roleName = "ROLE_" + (String) requestBody.get("roleName");
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

    @DeleteMapping("/roledelete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") int id) {
        Optional<ManageRole> roleToDelete = fakeRoleList.stream().filter(role -> role.getRoleID() == id).findFirst();
        if (roleToDelete.isPresent()) {
            fakeRoleList.remove(roleToDelete.get());
            return ResponseEntity.ok("Role with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Role with ID " + id + " not found.");
        }
    }

    @PostMapping("/roleupdate")
    public ResponseEntity<String> updateRole(@RequestBody Map<String, Object> updatedRole) {
        // Extract necessary fields from the request body
        int roleId = (int) updatedRole.get("roleID");
        String roleName = "ROLE_" + (String) updatedRole.get("roleName");
        String description = (String) updatedRole.get("description");

        // Check if the roleName already exists in the list
        boolean roleNameExists = fakeRoleList.stream()
                .anyMatch(role -> role.getRoleID() != roleId && role.getRoleName().equals(roleName));
        if (roleNameExists) {
            return ResponseEntity.badRequest().body("Role with roleName " + roleName + " already exists.");
        }

        // Find the role to update
        Optional<ManageRole> roleToUpdate = fakeRoleList.stream().filter(role -> role.getRoleID() == roleId)
                .findFirst();
        if (roleToUpdate.isPresent()) {
            ManageRole existingRole = roleToUpdate.get();
            // Update the role
            existingRole.setRoleName(roleName);
            existingRole.setDescription(description);
            return ResponseEntity.ok("Role updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Role with ID " + roleId + " not found.");
        }
    }

    // Note i have changed the idtest_cases to int
    @GetMapping("/getAllTestCases")
    public ResponseEntity<Object> getTestCases() {
        // Assuming you have logic to retrieve or generate the test case data
        String jsonString = 
            "{\"status\":\"success\",\"message\":[{\"createdBy\":\"John\",\"dateCreated\":\"2024-06-20T00:00:00Z\",\"dateUpdated\":\"2024-06-22T00:00:00Z\",\"deadline\":\"2024-06-25T00:00:00Z\",\"idtest_cases\":\"8801\",\"overallStatus\":\"Pending\",\"projectId\":\"01\",\"reason\":\"Reason01\",\"smartContractID\":\"5\",\"status\":\"Under Review\",\"testCaseName\":\"Test Case 1\",\"test_desc\":\"Desc1\",\"userID\":2001,\"username\":\"John\"},{\"createdBy\":\"John\",\"dateCreated\":\"2024-06-20T00:00:00Z\",\"dateUpdated\":\"2024-06-22T00:00:00Z\",\"deadline\":\"2024-06-25T00:00:00Z\",\"idtest_cases\":\"8802\",\"overallStatus\":\"Under Review\",\"projectId\":\"02\",\"reason\":\"Reason02\",\"smartContractID\":\"5\",\"status\":\"Under Review\",\"testCaseName\":\"Test Case 2\",\"test_desc\":\"Desc2\",\"userID\":2001,\"username\":\"John\"}]}";
  
        // Wrap the JSON string in a ResponseEntity with appropriate status code
        return ResponseEntity.ok(jsonString);
    }

}
