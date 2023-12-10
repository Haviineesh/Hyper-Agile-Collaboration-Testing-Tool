// package demo_ver.demo.service;

// import java.util.List;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @SpringBootApplication
// public class ShowNotification {

//     public static void main(String[] args) {
//         SpringApplication.run(ShowNotification.class, args);
//     }
// }

// @RestController
// @RequestMapping("/api")
// class NotificationController {

//     @GetMapping("/send-notification")
//     public String sendNotification() {
//         // Replace this with your actual notification logic
//         String notificationMessage = "Hello, this is a notification!";
//         sendNotificationToPostman(notificationMessage);
//         return "Notification sent successfully";
//     }

//     private void sendNotificationToPostman(String message) {
//         // You can use any notification mechanism here, like sending an email, SMS, etc.
//         // For simplicity, we will print the message to the console.
//         System.out.println("Notification: " + message);
//     }
// }