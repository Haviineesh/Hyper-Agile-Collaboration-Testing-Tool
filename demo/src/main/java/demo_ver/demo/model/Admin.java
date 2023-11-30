package demo_ver.demo.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class Admin {

    private int adminID;
    private String email;
    private String password;
    private String role;
    private String username;

    // Constructors
    public Admin() {
        // Default constructor
    }

    public Admin(String email, String password, String role, String username) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.username = username;
    }

    // Getter and Setter methods

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Additional methods

    public String manageRole() {
        // Implementation for managing roles
        return "Role management result";
    }

    public String ManageUser(){
        // Implementation for managing roles
        return "User management result";
    }
}
