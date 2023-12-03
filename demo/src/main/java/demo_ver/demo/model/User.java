package demo_ver.demo.model;

public class User {
    private String userId;
    private String name;
    private String password;
    private String contact;
    private String role;
    private String username;
    private String roleId;

    public User() {
    }

    public User(String userId, String name, String password, String contact, String role) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.contact = contact;
        this.role = role;
    }

    public User(String name, String password, String contact, String role) {
        this.name = name;
        this.password = password;
        this.contact = contact;
        this.role = role;
    }
    
     // Constructor for login
     public User(String username, String password, String roleId) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}