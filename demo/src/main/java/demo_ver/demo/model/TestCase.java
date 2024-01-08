package demo_ver.demo.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import demo_ver.demo.service.ManageUserService;
import demo_ver.demo.mail.MailService;

@EntityScan
public class TestCase {
    private Long idtest_cases;
    private String test_desc;
    private String deadline;
    private String dateUpdated;
    private String projectId;
    private String reason;
    private String status;
    private String testCaseName;
    private String dateCreated;
    // public int userID;
    private int smartContractID;
    private List<Integer> userID;


    public TestCase(){
    }

    public TestCase(Long idtest_cases, String projectId,int smartContractID, String testCaseName,String test_desc, String dateCreated, String deadline,
            String status, List<Integer> userID) {
        this.idtest_cases = idtest_cases;
        this.deadline = deadline;
        this.projectId = projectId;
        this.status = status;
        this.testCaseName = testCaseName;
        this.dateCreated = dateCreated;
        this.smartContractID = smartContractID;
        this.test_desc = test_desc;
        this.userID = userID;
    }

    public Long getIdtest_cases() {
        return idtest_cases;
    }
    public void setIdtest_cases(Long idtest_cases) {
        this.idtest_cases = idtest_cases;
    }
    public String getTest_desc() {
        return test_desc;
    }
    public void setTest_desc(String test_desc) {
        this.test_desc = test_desc;
    }
    public int getSmartContractID() {
        return smartContractID;
    }
    public void setSmartContractID(int smartContractID) {
        this.smartContractID = smartContractID;
    }    
    
    // Getters and Setters afser
   

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public String getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(String dateUpdated) { this.dateUpdated = dateUpdated; }

    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTestCaseName() { return testCaseName; }
    public void setTestCaseName(String testCaseName) { this.testCaseName = testCaseName; }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Integer> getUserID() {
        return userID;
    }

    public void setUserID(List<Integer> userID) {
        this.userID = userID;
    }

public List<String> getUsername() {
        return userID.stream()
                .map(userId -> {
                    ManageUser user = ManageUserService.getUserById(userId);
                    return (user != null) ? user.getUsername() : "";
                })
                .collect(Collectors.toList());
    }

    // check deadline and send notification
public void checkDeadlineAndSendNotification(MailService mailService) {
        if ("Pending".equals(status)) {
            LocalDate current = LocalDate.now();
            LocalDate deadlineDate = LocalDate.parse(deadline);

            if (current.isAfter(deadlineDate)) {
                // Deadline has been reached, send notification
                sendNotificationEmail(mailService);
            }
        }
    }

// send notification
    private void sendNotificationEmail(MailService mailService) {
        List<String> usernames = getUsername();
        String subject = "Test Case Approval Reminder";
        String message = "Dear user, the deadline for test case approval has been reached.";

        for (String username : usernames) {
            ManageUser user = ManageUserService.getUserByUsername(username);
            if (user != null && user.getEmail() != null) {
                String userEmail = user.getEmail();
                mailService.sendAssignedMail(userEmail, subject, message);
            }
        }
    }

}






    
    

