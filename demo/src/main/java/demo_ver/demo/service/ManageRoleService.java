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
            add(new ManageRole(1000, "Tester", "unit tester"));
            add(new ManageRole(1001, "Product Manager", "manage production"));
        }
    };

    public static List<ManageRole> getAllRoles() {
        return roleList;
    }

    public void addRole(ManageRole newRole) {
        if (roleList.stream().noneMatch(role -> role.getRoleName().equals(newRole.getRoleName()))) {
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
        deleteRole(manageRole.getRoleID());
        roleList.add(manageRole);

    }

}
