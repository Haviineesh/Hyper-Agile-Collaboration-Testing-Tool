package demo_ver.demo.service;
import org.springframework.stereotype.Service;


import demo_ver.demo.model.ManageUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ManageUserService {

    private static List<ManageUser> userList = new ArrayList<ManageUser>(Arrays.asList(
            new ManageUser(2001, 4055, "Teen", "123"),
            new ManageUser(2002, 5055, "hulk10 ", "abc")));

    public static List<ManageUser> getAllUsers() {
        return new ArrayList<ManageUser>(userList);
    }

    public void addUser(ManageUser newUser) {
        // Check if a user with the same ID already exists
        if (userList.stream().noneMatch(user -> user.getRoleID() == newUser.getRoleID())) {
            userList.add(newUser);
        } else {
            // Handle the case when a role with the same ID already exists
            // You can throw an exception, log a message, or take any other appropriate action.
            // For simplicity, let's log a message to the console.
            System.out.println("Role with ID " + newUser.getRoleID() + " already exists.");
        }
    }


}
