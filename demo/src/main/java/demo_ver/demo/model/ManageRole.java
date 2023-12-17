package demo_ver.demo.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class ManageRole {
    private int roleID;
    private String description;
    private String roleName;
    

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


}
