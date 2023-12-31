package demo_ver.demo.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class ManageUser {
    private int userID;
    public String email;
    public String username;
    public String password;
    public String role;

    public ManageUser() {

    }

    public ManageUser(int userID, String email, String username, String password, String role) {
        this.userID = userID;
        // this.roleID = roleID;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
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

    // public int getRoleID() {
    // return roleID;
    // }

    // public void setRoleID(int roleID) {
    // this.roleID = roleID;
    // }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}