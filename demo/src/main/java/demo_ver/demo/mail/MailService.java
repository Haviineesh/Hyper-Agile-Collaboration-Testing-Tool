package demo_ver.demo.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendMail(String recipient, MailStructure mailStructure) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(fromMail);
            simpleMailMessage.setSubject(mailStructure.getSubject());
            simpleMailMessage.setText(mailStructure.getMessage());
            simpleMailMessage.setTo(recipient);

            mailSender.send(simpleMailMessage);
        } catch (MailException e) {
            // Handle mail sending exceptions, e.g., log the error
            e.printStackTrace();
        }
    }

    public void sendAssignedMail(String recipient, String subject, String message) {
        MailStructure mailStructure = new MailStructure(recipient, subject, message);
        sendMail(recipient, mailStructure);
    }
}

