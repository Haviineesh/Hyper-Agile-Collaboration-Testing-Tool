package demo_ver.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo_ver.demo.model.ManageUser;

@Service
public class AuthService {

    @Autowired
    private ManageUserService manageUserService;

    public boolean changePassword(String username, String currentPassword, String newPassword) {
        ManageUser user = manageUserService.getUserByUsername(username);

        if (user != null && manageUserService.passwordMatches(currentPassword, user.getPassword())) {
            user.setPassword(newPassword);
            // Update the user's password in the user service
            manageUserService.updateUserPassword(user);
            return true;
        }

        return false;
    }
}
