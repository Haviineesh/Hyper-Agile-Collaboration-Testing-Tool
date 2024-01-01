package demo_ver.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import demo_ver.demo.service.ApiService;
import demo_ver.demo.service.ShowNotification.UseCase;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

            if (useCase.getDeadline() != null && useCase.getStatus() != null) {
                if (currentDateTime.isAfter(useCase.getDeadline())) {
                    if ("unchecked".equals(useCase.getStatus())) {
                        List<String> userRoles = apiService.getUserRoles(useCase);

                        for (String role : userRoles) {
                            sendEmail(role, "Use case deadline reached and status is unchecked for: " + useCase);
                            notifications.add("Email sent to user with role '" + role + "': " +
                                    "Use case deadline reached and status is unchecked.");
                        }
                    } else if ("checked".equals(useCase.getStatus())) {
                        sendEmail("anyRole", "Deadline reached, but status is checked for: " + useCase);
                        notifications.add("Email sent to user with role 'anyRole': " +
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

    private void sendEmail(String recipient, String message) {
        String emailUsername = System.getenv("EMAIL_USERNAME");
        String emailPassword = System.getenv("EMAIL_PASSWORD");
    
        if (emailUsername == null || emailPassword == null) {
            logAndThrowConfigurationException("Email credentials not configured.");
        }
    
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
    
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUsername, emailPassword);
            }
        };
    
        Session session = Session.getInstance(properties, authenticator);
    
        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(emailUsername));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            mimeMessage.setSubject("Notification");
            mimeMessage.setText(message);
    
            Transport.send(mimeMessage);
    
            System.out.println("Email sent to '" + recipient + "': " + message);
        } catch (MessagingException e) {
            logAndThrowConfigurationException("Failed to send email: " + e.getMessage());
        }
    }

    private void logAndThrowConfigurationException(String errorMessage) {
        // Log the error
        System.err.println(errorMessage);

        // Throw a runtime exception with the error message
        throw new RuntimeException(errorMessage);
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



