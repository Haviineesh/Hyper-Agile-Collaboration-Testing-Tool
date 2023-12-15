package demo_ver.demo.config;

import demo_ver.demo.service.ApiService;
import demo_ver.demo.service.ShowNotification.DummyApiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiServiceConfig {

    @Bean
    public ApiService apiService() {
        return new DummyApiService(); // Replace with your actual implementation
    }
}
