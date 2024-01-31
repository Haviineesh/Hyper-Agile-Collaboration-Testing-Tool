package demo_ver.demo.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.client.RestTemplate;

import demo_ver.demo.service.ManageRoleService;

@EntityScan
public class ManageUser {
    private int userID;
    public String email;
    public String username;
    public String password;
    public int roleID;
    private String resetToken;

    private final RestTemplate restTemplate = new RestTemplate();

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

    @Autowired
    private ManageRoleService manageRoleService = new ManageRoleService(restTemplate);

    public String getRoleName() {
        String roleName = manageRoleService.apiFindByIdString(roleID);
        return (roleName != null) ? roleName : "";
    }

    public List<GrantedAuthority> getAuthorities() {
        return manageRoleService.apiFindByIdList(roleID).getAuthorities();
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

}