package demo_ver.demo.model;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class ManageRole {
    private int roleID;
    public String description;
    public String roleName;

    class roleRepository {

        public static Optional<ManageRole> findById(int roleID) {
            return null;
        }

        public static void deleteById(int roleID) {
        }

        public static List<ManageRole> findAll() {
            return null;
        }

    }

    public ManageRole() {

    }

    public ManageRole(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

    public ManageRole(int roleID, String roleName, String description) {
        this.roleID = roleID;
        this.roleName = roleName;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String addNewRole(String roleName, String description) {
        // Implementation for adding a new role
        ManageRole newRole = new ManageRole();
        newRole.setRoleName(roleName);
        newRole.setDescription(description);

        // Save the new role to the database (assuming you have a repository)
        // roleRepository.save(newRole);

        return "New role added";
    }

    public String deleteRole(int roleID) {
        // Implementation for deleting a role
        // Delete the role from the database (assuming you have a repository)
        Optional<ManageRole> existingRoleOptional = roleRepository.findById(roleID);

        // Check if the role exists
        if (existingRoleOptional.isPresent()) {
            // Delete the role from the database
            roleRepository.deleteById(roleID);

            return "Role deleted successfully";
        } else {
            return "Role not found";
        }
    }

    public String editRole(int roleID, String newRoleName, String newDescription) {
        // Implementation for editing a role
        // Retrieve the existing role from the database (assuming you have a repository)
        ManageRole existingRole = roleRepository.findById(roleID).orElse(null);

        // Check if the role exists
        if (existingRole != null) {
            // Update the role properties
            if (newRoleName != null && !newRoleName.isEmpty()) {
                existingRole.setRoleName(newRoleName);
            }
            if (newDescription != null && !newDescription.isEmpty()) {
                existingRole.setDescription(newDescription);
            }

            // Save the updated role to the database (assuming you have a repository)
            // roleRepository.save(existingRole);

            return "Role edited";
        } else {
            return "Role not found";
        }
    }

    public List<ManageRole> viewRoleList() {
        // Implementation for viewing a list of roles
        // Retrieve the list of roles from the database (assuming you have a repository)
        // List<ManageRole> roles = roleRepository.findAll();

        // You may also add additional logic to filter or customize the list as needed
        List<ManageRole> roles = roleRepository.findAll();

        return roles;
    }

}
