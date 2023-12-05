package demo_ver.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import demo_ver.demo.model.ManageRole;

@Service
public class ManageRoleService {

    private static List<ManageRole> roleList = new ArrayList<ManageRole>(Arrays.asList(
            new ManageRole(4055, "Tester", "unit tester"),
            new ManageRole(5055, "Product Manager", "manage production")));

    public List<ManageRole> getAllRoles() {
        return new ArrayList<ManageRole>(roleList);
    }

    public void addRole(ManageRole newRole) {
        // Check if a role with the same ID already exists
        if (roleList.stream().noneMatch(role -> role.getRoleID() == newRole.getRoleID())) {
            roleList.add(newRole);
        } else {
            // Handle the case when a role with the same ID already exists
            // You can throw an exception, log a message, or take any other appropriate action.
            // For simplicity, let's log a message to the console.
            System.out.println("Role with ID " + newRole.getRoleID() + " already exists.");
        }
    }

}
