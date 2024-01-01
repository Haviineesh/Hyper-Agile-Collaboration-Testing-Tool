package demo_ver.demo.service;

import demo_ver.demo.service.ShowNotification.UseCase;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    @Override
    public List<String> getUserRoles(UseCase useCase) {
        // Implement your logic to retrieve user roles based on the provided UseCase
        // This is a mock implementation, replace it with your actual logic
        return Arrays.asList("ROLE_USER", "ROLE_ADMIN");
    }
}
