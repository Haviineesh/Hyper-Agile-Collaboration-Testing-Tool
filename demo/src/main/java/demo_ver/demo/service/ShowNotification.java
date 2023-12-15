package demo_ver.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowNotification {

    @Autowired
    private ApiService apiService;

    public static class UseCase {
        private LocalDateTime deadline;
        private String status;
        private List<String> roles;

        // Constructors, getters, and setters 
        public UseCase() {
        }

        public UseCase(LocalDateTime deadline, String status, List<String> roles) {
            this.deadline = deadline;
            this.status = status;
            this.roles = roles;
        }

        // Getters and Setters
        public LocalDateTime getDeadline() {
            return deadline;
        }

        public void setDeadline(LocalDateTime deadline) {
            this.deadline = deadline;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }

        @Override
        public String toString() {
            return "UseCase{" +
                    "deadline=" + deadline +
                    ", status='" + status + '\'' +
                    ", roles=" + roles +
                    '}';
        }
    }

    // This method sends notifications based on the specified criteria.
    public void sendNotifications(List<UseCase> useCases, ApiService apiService) {
        for (UseCase useCase : useCases) {
            LocalDateTime currentDateTime = LocalDateTime.now();

            // Check if the deadline is not null, the status is not null, and the status is unchecked.
            if (useCase.getDeadline() != null &&
                useCase.getStatus() != null &&
                currentDateTime.isAfter(useCase.getDeadline()) &&
                "unchecked".equals(useCase.getStatus())) {
                
                // Get the roles of the user associated with this use case.
                List<String> userRoles = apiService.getUserRoles(useCase);

                // Iterate through user roles to send notifications.
                for (String role : userRoles) {
                    // Send notification to the user with the specific role.
                    sendNotification(role, "Use case deadline reached and status is unchecked for: " + useCase);
                }
            }
        }
    }

    // Method to simulate sending a notification.
    private void sendNotification(String userRole, String message) {
        // Implement logic to send a notification to the user with the specified role.
        // This could involve using a messaging system or an API call.
        // For the sake of the example, let's print the notification.

        System.out.println("Notification sent to user with role '" + userRole + "': " + message);
    }

    public static void main(String[] args) {
        // Create instances of the NotificationService, UseCase, and ApiService for testing.
        ShowNotification notificationService = new ShowNotification();
        UseCase useCase = new UseCase();
        // Set relevant properties for the use case.

        // Create a list of use cases.
        List<UseCase> useCases = List.of(useCase);

        // Create an instance of the DummyApiService for testing.
        ApiService apiService = new DummyApiService();

        // Call the sendNotifications method to check and send notifications.
        notificationService.sendNotifications(useCases, apiService);
    }

    // Dummy implementation of ApiService for testing
    public static class DummyApiService implements ApiService {

        @Override
        public List<String> getUserRoles(UseCase useCase) {
            // Simulate API call to retrieve user roles based on the use case
            // Replace this with your actual API logic
            // For the sake of the example, let's return a sample list of roles.
            return useCase.getRoles();
        }
    }
}
