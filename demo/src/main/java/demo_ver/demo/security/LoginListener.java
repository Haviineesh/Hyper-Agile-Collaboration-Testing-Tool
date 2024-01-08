package demo_ver.demo.security;

import demo_ver.demo.mail.MailService;
import demo_ver.demo.mail.MailStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private MailService mailService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String userEmail = event.getAuthentication().getName();
        sendLoginNotification(userEmail);
    }

    private void sendLoginNotification(String userEmail) {
        String subject = "Login Notification";
        String message = "Dear user, you have successfully logged in.";
        mailService.sendAssignedMail(userEmail, subject, message);
    }
}

