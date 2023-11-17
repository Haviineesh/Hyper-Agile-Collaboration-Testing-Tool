package demo_ver.demo.User;

public class User {
    private String userId;
    private String name;
    private String password;
    private String contact;
    private String role;

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

}