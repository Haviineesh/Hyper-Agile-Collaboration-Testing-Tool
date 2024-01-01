package demo_ver.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import demo_ver.demo.model.ManageRole;

@Service
public class ManageRoleService {

    private static List<ManageRole> roleList = new ArrayList<ManageRole>() {
        {
             add(new ManageRole(1000, "Admin", "Administration"));
            add(new ManageRole(1001, "Tester", "unit tester"));
            add(new ManageRole(1002, "Product Manager", "manage production"));
            add(new ManageRole(1003, "Developer", "Programming"));

        }
    };

    public static List<ManageRole> getAllRoles() {
        return roleList;
    }

    public void addRole(ManageRole newRole) {
        if (roleList.isEmpty()) {
            // If the roleList is empty, set the roleID to 1
            newRole.setRoleID(1000);
            roleList.add(newRole);
        } else if (roleList.stream().noneMatch(role -> role.getRoleName().equals(newRole.getRoleName()))) {
            newRole.setRoleID(roleList.get(roleList.size() - 1).getRoleID() + 1);
            roleList.add(newRole);
        } else {
            // Handle the case when a role with the same roleName already exists
            // You can throw an exception, log a message, or take any other appropriate
            // action.
            // For simplicity, let's log a message to the console.
            System.out.println("Role with roleName " + newRole.getRoleName() + " already exists.");
        }
    }

    public Optional<ManageRole> findById(int id) {
        return roleList.stream().filter(t -> t.getRoleID() == id).findFirst();
    }

    public void deleteRole(int id) {
        roleList.removeIf(t -> t.getRoleID() == id);
    }

    public void updateManageRole(ManageRole manageRole) {
        Optional<ManageRole> existingRoleOptional = findById(manageRole.getRoleID());

        if (existingRoleOptional.isPresent()) {
            ManageRole existingRole = existingRoleOptional.get();

            existingRole.setDescription(manageRole.getDescription());
            // Check if the new roleName is not already in use
            if (roleList.stream().noneMatch(role -> role.getRoleName().equals(manageRole.getRoleName()))) {
                // Update the existing role's properties
                existingRole.setRoleName(manageRole.getRoleName());
                System.out.println("Role with ID " + existingRole.getRoleID() + " updated successfully");
            } else {
                System.out.println("Role with roleName " + manageRole.getRoleName() + " already exists.");
            }
        } else {
            // Handle the case when the role does not exist
            System.out.println("Role with ID " + manageRole.getRoleID() + " not found");
        }
    }

}
