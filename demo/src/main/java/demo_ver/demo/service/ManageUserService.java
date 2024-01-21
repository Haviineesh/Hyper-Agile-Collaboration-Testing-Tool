package demo_ver.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import demo_ver.demo.model.ManageUser;
import demo_ver.demo.mail.MailService;

@Service
public class ManageUserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private static List<ManageUser> userList;

    @Autowired
    private MailService mailService;

    public ManageUserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        initializeUserList();
    }

    // Initialize the user list with some sample data
    private void initializeUserList() {
        userList = new ArrayList<>();
        userList.add(new ManageUser(2000, "teeneshsubramaniam10@gmail.com", "Teenesh", passwordEncoder.encode("123456"),
                1000));
        userList.add(new ManageUser(2001, "user@gmail.com", "John", passwordEncoder.encode("654321"), 1002));
        userList.add(
                new ManageUser(2002, "williamlik@graduate.utm.my", "Will", passwordEncoder.encode("142536"), 1001));
        userList.add(
                new ManageUser(2003, "Mahathir@gmail.com", "Mahathir", passwordEncoder.encode("100000"), 1003));
    }

    // Get all users in the system
    public static List<ManageUser> getAllUsers() {
        return userList;
    }

    // Add a new user to the system
    public void addUser(ManageUser newUser, int roleID) {
        if (isUserUnique(newUser)) {
            String plainTextPassword = newUser.getPassword(); // Get the plain text password before encoding
            newUser.setUserID(generateUserID());
            newUser.setRoleID(roleID);
            userList.add(newUser);

            // send email notification to new user with plain text password
            sendNewUserNotification(newUser, plainTextPassword);

            // Now, encode and set the password
            newUser.setPassword(passwordEncoder.encode(plainTextPassword));
        } else {
            // Handle duplicate user logic if needed
        }
    }

    // send email to new user
    private void sendNewUserNotification(ManageUser newUser, String plainTextPassword) {
        String subject = "Welcome to the System";
        String message = "Dear " + newUser.getUsername() + ",\n\n"
                + "Welcome to our system! Your account has been successfully created.\n"
                + "Username: " + newUser.getUsername() + "\n"
                + "Password: " + plainTextPassword + "\n"
                + "Please log in and change your password.\n\n"
                + "Best regards,\nThe System Team";

        mailService.sendAssignedMail(newUser.getEmail(), subject, message);
    }

    // Check if a user with the same username or email already exists
    private boolean isUserUnique(ManageUser newUser) {
        return userList.stream().noneMatch(user -> user.getUsername().equalsIgnoreCase(newUser.getUsername()) ||
                user.getEmail().equalsIgnoreCase(newUser.getEmail()));
    }

    // Delete a user by userID
    public void deleteUser(int userID) {
        userList.removeIf(user -> user.getUserID() == userID);
    }

    // Generate a new unique userID
    private int generateUserID() {
        return userList.get(userList.size() - 1).getUserID() + 1;
    }

    // Check if a username exists in the system
    public boolean isUsernameExists(String username) {
        return userList.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    // Check if an email exists in the system
    public boolean isEmailExists(String email) {
        return userList.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    // Get a user by userID
    public static ManageUser getUserById(int userID) {
        return userList.stream()
                .filter(user -> user.getUserID() == userID)
                .findFirst()
                .orElse(null);
    }

    // Check if a username exists in the system excluding the current user
    public boolean isUsernameExistsExcludingCurrentUser(String username, int userID) {
        return userList.stream()
                .anyMatch(user -> user.getUserID() != userID && user.getUsername().equalsIgnoreCase(username));
    }

    // Check if an email exists in the system excluding the current user
    public boolean isEmailExistsExcludingCurrentUser(String email, int userID) {
        return userList.stream()
                .anyMatch(user -> user.getUserID() != userID && user.getEmail().equalsIgnoreCase(email));
    }

    // Update user details (including role)
    public void updateUser(ManageUser updatedUser, int roleID) {
        userList.stream()
                .filter(user -> user.getUserID() == updatedUser.getUserID())
                .findFirst()
                .ifPresent(user -> {
                    user.setEmail(updatedUser.getEmail());
                    user.setUsername(updatedUser.getUsername());
                    user.setRoleID(roleID);
                });
    }

    // Retrieve user details for authentication
    public ManageUser getUserByUsername(String username) {
        return userList.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    // Load user details for authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ManageUser manageUser = getUserByUsername(username);

        if (manageUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        List<GrantedAuthority> authorities = getAuthorities(manageUser.getRoleName());

        // Log user details and authorities
        System.out.println("User Details: ");
        System.out.println("Username: " + manageUser.getUsername());
        System.out.println("Password: " + manageUser.getPassword());
        System.out.println("Authorities: " + authorities);

        // Update the encoded password from ManageUser
        return new User(
                manageUser.getUsername(),
                manageUser.getPassword(), // Use the updated encoded password
                authorities);
    }

    // Get authorities for a user role
    private List<GrantedAuthority> getAuthorities(String role) {
        return new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority(role)));
    }

    // Check if a raw password matches an encoded password
    public boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // Update user password
    public void updateUserPassword(ManageUser user) {
        userList.stream()
                .filter(existingUser -> existingUser.getUserID() == user.getUserID())
                .findFirst()
                .ifPresent(existingUser -> existingUser.setPassword(passwordEncoder.encode(user.getPassword())));
    }

    // Find a user by reset token
    public ManageUser findUserByResetToken(String resetToken) {
        return userList.stream()
                .filter(user -> Objects.equals(user.getResetToken(), resetToken))
                .findFirst()
                .orElse(null);
    }

    // Update reset token for a user
    public void updateResetToken(ManageUser user, String resetToken) {
        user.setResetToken(resetToken);
    }

    // Check if a reset token is valid (dummy implementation)
    public boolean isValidToken(String token) {
        return true;
    }

    // Generate a reset token (dummy implementation)
    public String generateResetToken(String email) {
        return UUID.randomUUID().toString();
    }

    // Check if a password is valid
    public boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    // Update user password with a new password
    public void updateUserPassword(ManageUser user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
    }

    // Get a user by email
    public ManageUser getUserByEmail(String email) {
        return userList.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
