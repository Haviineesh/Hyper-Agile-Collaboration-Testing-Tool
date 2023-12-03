package demo_ver.demo.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public boolean validateLogin(String username, String password, String roleId) {
        // Simulating login validation
        return "teen".equals(username) &&
                "123admin".equals(password) &&
                "admin".equals(roleId);
    }
}

