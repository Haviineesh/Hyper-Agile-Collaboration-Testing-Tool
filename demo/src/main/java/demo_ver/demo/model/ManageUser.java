package demo_ver.demo.model;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class ManageUser {
    private int userID;
    private int roleID;
    public String username;
    public String password;

    class userRepository {

        public static Optional<ManageUser> findById(int userID) {
            return null;
        }

        public static void deleteById(int userID) {
        }

        public static List<ManageUser> findAll() {
            return null;
        }

    }

    public ManageUser() {

    }

    public ManageUser(int userID, int roleID, String username, String password) {
        this.userID = userID;
        this.roleID = roleID;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
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

    // public String addNewUser(String username, String password) {
    //     // Implementation for adding a new user
    //     ManageUser newUser = new ManageUser();
    //     newUser.setUsername(username);
    //     newUser.setPassword(password);

    //     // Save the new user to the database (assuming you have a repository)
    //     // userRepository.save(newUser);

    //     return "New user added";
    // }

    // public String deleteUser(int userID) {
    //     // Implementation for deleting a user
    //     // Delete the user from the database (assuming you have a repository)
    //     Optional<ManageUser> existingUserOptional = userRepository.findById(userID);

    //     // Check if the user exists
    //     if (existingUserOptional.isPresent()) {
    //         // Delete the user from the database
    //         userRepository.deleteById(userID);

    //         return "User deleted successfully";
    //     } else {
    //         return "User not found";
    //     }
    // }

    public List<ManageUser> viewUserList() {
        // Implementation for viewing a list of users
        // Retrieve the list of users from the database (assuming you have a repository)
        // List<ManageUser> users = userRepository.findAll();

        // You may also add additional logic to filter or customize the list as needed
        List<ManageUser> users = userRepository.findAll();

        return users;
    }

}