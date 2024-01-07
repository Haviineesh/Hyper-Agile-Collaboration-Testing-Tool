package demo_ver.demo.model;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import demo_ver.demo.service.ManageRoleService;

@EntityScan
public class ManageUser {
    private int userID;
    public String email;
    public String username;
    public String password;
    public int roleID;

    public ManageUser() {

    }

    public ManageUser(int userID, String email, String username, String password, int roleID) {
        this.userID = userID;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roleID = roleID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        ManageRole role = ManageRoleService.getRoleById(roleID);
        return (role != null) ? role.getRoleName() : "";
    }

}