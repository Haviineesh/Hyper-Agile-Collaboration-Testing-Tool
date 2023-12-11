package demo_ver.demo.service;
import org.springframework.stereotype.Service;

import demo_ver.demo.model.ManageUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ManageUserService {

    private static List<ManageUser> userList = new ArrayList<ManageUser>(Arrays.asList(
            new ManageUser( 1001, "teen@gmail.com", "Teen", "123", "Admin"),
            new ManageUser( 1002, "ali@gmail.com", "ali", "abc", "Software Tester")));
            

    public static List<ManageUser> getAllUsers() {
        return new ArrayList<ManageUser>(userList);
    }

    public void addUser(ManageUser newUser) {
        // Check if a user with the same user ID already exists
        if (userList.stream().noneMatch(user -> user.getUserID() == newUser.getUserID() ||
        user.getUsername().equalsIgnoreCase(newUser.getUsername()) ||
        user.getEmail().equalsIgnoreCase(newUser.getEmail()))) {
 
            userList.add(newUser);
        } else {
            // Handle the case when a user with the same user ID already exists
            // You can throw an exception, log a message, or take any other appropriate action.
            // For simplicity, let's log a message to the console.
            System.out.println("User with ID, username, or email already exists.");
        }
    }


    public boolean isUsernameExists(String username) {
        return userList.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

     public boolean isEmailExists(String email) {
        return userList.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

}
