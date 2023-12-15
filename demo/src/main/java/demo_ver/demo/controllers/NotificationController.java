package demo_ver.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import demo_ver.demo.service.ApiService;
import demo_ver.demo.service.ShowNotification.UseCase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private ApiService apiService;

    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<NotificationResponse> sendNotifications(@RequestBody List<UseCase> useCases) {
        List<String> notifications = new ArrayList<>();

        if (useCases == null || useCases.isEmpty()) {
            return ResponseEntity.badRequest().body(new NotificationResponse("No use cases provided."));
        }

        for (UseCase useCase : useCases) {
            LocalDateTime currentDateTime = LocalDateTime.now();

            // Check if the deadline is not null and the status is not null.
            if (useCase.getDeadline() != null && useCase.getStatus() != null) {
                if (currentDateTime.isAfter(useCase.getDeadline())) {
                    if ("unchecked".equals(useCase.getStatus())) {
                        List<String> userRoles = apiService.getUserRoles(useCase);

                        for (String role : userRoles) {
                            sendNotification(role, "Use case deadline reached and status is unchecked for: " + useCase);
                            notifications.add("Notification sent to user with role '" + role + "': " +
                                    "Use case deadline reached and status is unchecked.");
                        }
                    } else if ("checked".equals(useCase.getStatus())) {
                        sendNotification("anyRole", "Deadline reached, but status is checked for: " + useCase);
                        notifications.add("Notification sent to user with role 'anyRole': " +
                                "Deadline reached, but status is checked.");
                    }
                } else {
                    if ("unchecked".equals(useCase.getStatus())) {
                        notifications.add("Date Line not reached and Status \"Unchecked\".");
                    } else if ("checked".equals(useCase.getStatus())) {
                        notifications.add("Date Line not reached but Status \"Checked\".");
                    }
                }
            } else {
                notifications.add("Invalid use case provided.");
            }
        }

        return ResponseEntity.ok(new NotificationResponse(notifications));
    }

    @RequestMapping("/send-html")
    public String sendNotificationsHtml(
            @RequestParam String deadline,
            @RequestParam String status,
            @RequestParam String roles,
            Model model
    ) {
        List<String> notifications = new ArrayList<>();
        UseCase useCase = new UseCase();
        useCase.setDeadline(LocalDateTime.parse(deadline));
        useCase.setStatus(status);
        useCase.setRoles(List.of(roles.split(",")));

        ResponseEntity<NotificationResponse> responseEntity = sendNotifications(List.of(useCase));

        model.addAttribute("notifications", responseEntity.getBody().getNotifications());
        return "notificationResult";
    }

    private void sendNotification(String userRole, String message) {
        System.out.println("Notification sent to user with role '" + userRole + "': " + message);
    }

    private static class NotificationResponse {
        private final List<String> notifications;

        public NotificationResponse(List<String> notifications) {
            this.notifications = notifications;
        }

        public NotificationResponse(String message) {
            this.notifications = new ArrayList<>();
            this.notifications.add(message);
        }

        public List<String> getNotifications() {
            return notifications;
        }
    }
}
