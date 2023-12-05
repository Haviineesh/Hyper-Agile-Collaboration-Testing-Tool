package demo_ver.demo.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public boolean validateLogin(String username, String password, String roleId) {
        
          // Log received values
          System.out.println("Received username: " + username);
          System.out.println("Received password: " + password);
          System.out.println("Received roleId: " + roleId);
          
        // Simulating login validation
        return "teen".equals(username) &&
                "123".equals(password) &&
                "a10".equals(roleId);
    }
}

