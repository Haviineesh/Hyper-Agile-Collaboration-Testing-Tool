package demo_ver.demo.model;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;



@EntityScan
public class ManageUser {
    private int userID;
    public String email;
    public String username;
    public String password;
    public String role;

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
    //     return roleID;
    // }

    // public void setRoleID(int roleID) {
    //     this.roleID = roleID;
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

    public void setPassword(String password){
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String addNewUser(int roleID, String email, String username, String password, String role) {
        // Implementation for adding a new user
        ManageUser newUser = new ManageUser();
        newUser.setUserID(userID);
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(role);

        // Save the new user to the database (assuming you have a repository)
        // userRepository.save(newUser);

        return "New user added";
    }

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