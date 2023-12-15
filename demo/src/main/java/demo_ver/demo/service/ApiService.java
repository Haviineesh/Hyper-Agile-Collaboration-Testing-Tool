package demo_ver.demo.service;

import java.util.List;

// Interface representing the backend API service
public interface ApiService {
    List<String> getUserRoles(ShowNotification.UseCase useCase);
}
