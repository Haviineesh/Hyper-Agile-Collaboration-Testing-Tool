package demo_ver.demo.security;

import demo_ver.demo.mail.MailService;
import demo_ver.demo.model.ManageUser;
import demo_ver.demo.service.ManageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private MailService mailService;

    @Autowired
    private ManageUserService manageUserService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        sendLoginNotification(username);
    }

    private void sendLoginNotification(String username) {
        // // Retrieve the user by username
        // ManageUser user = manageUserService.getUserByUsername(username);

        // // Check if the user exists and has an email
        // if (user != null && user.getEmail() != null) {
        //     String userEmail = user.getEmail();
        //     String subject = "Login Notification";
        //     String message = "Dear user, you have successfully logged in. Please check your test cases under you.";
        //     mailService.sendAssignedMail(userEmail, subject, message);
        // }
    }
}




