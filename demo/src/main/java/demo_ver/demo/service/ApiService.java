package demo_ver.demo.service;

import demo_ver.demo.service.ShowNotification.UseCase;

import java.util.List;

public interface ApiService {
    List<String> getUserRoles(UseCase useCase);
}


