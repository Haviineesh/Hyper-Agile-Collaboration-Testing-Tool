package demo_ver.demo.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean validateLogin(String username, String password, String role) {
        
        return "teenesh".equals(username) &&
                "123456".equals(password) &&
                "admin".equals(role);
    }


    public boolean validatePassword(String password) {
      return password.length() >= 6;
  }

    public boolean matchPasswords(String newPassword, String confirmPassword) {
      return newPassword.equals(confirmPassword);
  }
}

