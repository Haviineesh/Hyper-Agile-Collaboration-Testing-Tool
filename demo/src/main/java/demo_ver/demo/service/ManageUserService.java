package demo_ver.demo.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import demo_ver.demo.model.ManageUser;

@Service
public class ManageUserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private static List<ManageUser> userList;

    // private static List<ManageUser> userList = new ArrayList<ManageUser>() {
    // {
    // add(new ManageUser(2000, "teenesh@gmail.com", "Teenesh", "123456", 1000));
    // add(new ManageUser(2001, "user@gmail.com", "John", "654321", 1002));
    // }
    // };

    private void initializeUserList() {
        userList = new ArrayList<>();
        userList.add(new ManageUser(2000, "teenesh@gmail.com", "Teenesh", passwordEncoder.encode("123456"), 1000));
        userList.add(new ManageUser(2001, "user@gmail.com", "John", passwordEncoder.encode("654321"), 1002));
    }

    public ManageUserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        initializeUserList();
    }

    public static List<ManageUser> getAllUsers() {
        return userList;
    }

    public void addUser(ManageUser newUser, int roleID) {
        // Check if a user with the same user ID already exists
        if (userList.stream().noneMatch(user -> user.getUsername().equalsIgnoreCase(newUser.getUsername()) ||
                user.getEmail().equalsIgnoreCase(newUser.getEmail()))) {

            // Print the raw password before encoding
            System.out.println("Raw Password: " + newUser.getPassword());

            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

            // Print the encoded password after encoding
            System.out.println("Encoded Password: " + newUser.getPassword());
            newUser.setUserID(generateUserID()); // Assign a new user ID
            newUser.setRoleID(roleID);
            userList.add(newUser);

        } else {
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
                    // Consider handling password updates carefully, especially with regards to
                    // security
                });
    }

    // retrieve user details for authentication
    public ManageUser getUserByUsername(String username) {
        return userList.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ManageUser manageUser = getUserByUsername(username); // retrieve ManageUser

        if (manageUser == null) {
            throw new UsernameNotFoundException("User not found with username: " +
                    username);
        }

        List<GrantedAuthority> authorities = getAuthorities(manageUser.getRoleName());

        // Log user details and authorities
        System.out.println("User Details: ");
        System.out.println("Username: " + manageUser.getUsername());
        System.out.println("Password: " + manageUser.getPassword());
        System.out.println("Authorities: " + authorities);

        // Use the encoded password from ManageUser
        return new User(
                manageUser.getUsername(),
                manageUser.getPassword(), // Use the encoded password
                authorities);
    }

    private List<GrantedAuthority> getAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        // Assuming roles are prefixed with "ROLE_"
        return authorities;
    }

    // Change password methods
    public boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void updateUserPassword(ManageUser user) {
        // Find the existing user and update the password
        userList.stream()
                .filter(existingUser -> existingUser.getUserID() == user.getUserID())
                .findFirst()
                .ifPresent(existingUser -> existingUser.setPassword(passwordEncoder.encode(user.getPassword())));
    }
}
