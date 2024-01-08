package demo_ver.demo.security;

import demo_ver.demo.mail.MailService;
import demo_ver.demo.mail.MailStructure;
import demo_ver.demo.model.ManageUser;
import demo_ver.demo.model.TestCase;
import demo_ver.demo.service.ManageUserService;
import demo_ver.demo.service.ViewCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private MailService mailService;

    @Autowired
    private ManageUserService manageUserService;

    @Autowired
    private ViewCaseService viewCaseService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        sendLoginNotification(username);
    }

    private void sendLoginNotification(String username) {
        // Retrieve the user by username
        ManageUser user = manageUserService.getUserByUsername(username);

        // Check if the user exists and has an email
        if (user != null && user.getEmail() != null) {
            String userEmail = user.getEmail();

            // Check for pending test cases with deadlines
            List<TestCase> pendingTestCases = viewCaseService.getPendingTestCasesForUser(user.getUserID());
            for (TestCase testCase : pendingTestCases) {
                LocalDate current = LocalDate.now();
                LocalDate deadlineDate = LocalDate.parse(testCase.getDeadline());

                if (current.isAfter(deadlineDate)) {
                    // Deadline has been reached, send notification
                    String subject = "Test Case Approval Reminder";
                    String message = "Dear user, the deadline for test case approval has been reached.";
                    mailService.sendAssignedMail(userEmail, subject, message);
                    break;  // Send only one notification per login
                }
            }
        }
    }
}



