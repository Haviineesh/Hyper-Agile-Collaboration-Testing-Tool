package demo_ver.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import demo_ver.demo.model.ManageUser;

@Service
public class ManageUserService {

    private static List<ManageUser> userList = new ArrayList<ManageUser>() {
        {
            add(new ManageUser(2000, "teenesh@gmail.com", "Teenesh", "123456", 1001));
            add(new ManageUser(2001, "user@gmail.com", "John", "654321", 1002));
        }
    };

    public static List<ManageUser> getAllUsers() {
        return userList;
    }

    public void addUser(ManageUser newUser, int roleID) {
        // Check if a user with the same user ID already exists
        if (userList.stream().noneMatch(user -> user.getUsername().equalsIgnoreCase(newUser.getUsername()) ||
                user.getEmail().equalsIgnoreCase(newUser.getEmail()))) {

            newUser.setUserID(generateUserID()); // Assign a new user ID
            newUser.setRoleID(roleID);
            userList.add(newUser);

            // Invoke notification method here

        } else {
            System.out.println("User with username or email already exists.");
        }
    }

    public void deleteUser(int userID) {
    userList.removeIf(user -> user.getUserID() == userID);
    }

    private int generateUserID() {
        return userList.get(userList.size() - 1).getUserID() + 1;
    }

    public boolean isUsernameExists(String username) {
        return userList.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    public boolean isEmailExists(String email) {
        return userList.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    public static ManageUser getUserById(int userID) {
        return userList.stream()
                .filter(user -> user.getUserID() == userID)
                .findFirst()
                .orElse(null); 
    }

    public void updateUser(ManageUser updatedUser, int roleID) {
        // Find the existing user and update their details
        userList.stream()
                .filter(user -> user.getUserID() == updatedUser.getUserID())
                .findFirst()
                .ifPresent(user -> {
                    user.setEmail(updatedUser.getEmail());
                    user.setUsername(updatedUser.getUsername());
                    user.setRoleID(updatedUser.getRoleID());
                    // Consider handling password updates carefully, especially with regards to security
                });
    }

}
