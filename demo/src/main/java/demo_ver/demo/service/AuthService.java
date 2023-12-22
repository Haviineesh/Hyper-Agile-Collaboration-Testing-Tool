package demo_ver.demo.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean validateLogin(String username, String password, String roleId) {
        
          System.out.println("Received username: " + username);
          System.out.println("Received password: " + password);
          System.out.println("Received roleId: " + roleId);
          
        // Simulating login validation
        return "teen".equals(username) &&
                "123".equals(password) &&
                "a10".equals(roleId);
    }


    public boolean validatePassword(String password) {
      return password.length() >= 6;
  }

    public boolean matchPasswords(String newPassword, String confirmPassword) {
      return newPassword.equals(confirmPassword);
  }
}

