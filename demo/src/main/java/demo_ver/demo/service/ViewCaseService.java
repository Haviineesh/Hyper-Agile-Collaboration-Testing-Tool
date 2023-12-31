package demo_ver.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import demo_ver.demo.model.ManageUser;
import demo_ver.demo.model.TestCase;
import demo_ver.demo.mail.MailService;
import demo_ver.demo.mail.MailStructure;
import demo_ver.demo.utils.RandomNumber;




@Service
public class ViewCaseService {

     private static List<TestCase> testList = new ArrayList<TestCase>(){
     {
            add(new TestCase(RandomNumber.getRandom(100,999),"001",8,"Diagram","desc1","2023-12-11","2023-11-10","Pending",Arrays.asList(2000)));
            add(new TestCase(RandomNumber.getRandom(100,999),"002",15,"Package","desc23","2023-11-07","2023-11-17","Pending",Arrays.asList(2001)));
            add(new TestCase(RandomNumber.getRandom(100,999),"003",17,"Behavorial","desc34","2023-12-05","2023-11-15","Pending",Arrays.asList(2001)));
            add(new TestCase(RandomNumber.getRandom(100,999),"004",19,"Diagram","desc56","2023-12-20","2024-01-07","Pending",Arrays.asList(2002)));
     }
    };

    @Autowired
    private MailService mailService;

    public static List<TestCase> findAllList() {
       return testList;
    }


    public void addTestCaseForm(TestCase testCase, List<Integer> userID) {
        
        testCase.setIdtest_cases(RandomNumber.getRandom(100,999));
        testCase.setUserID(userID);
        testList.add(testCase);
    }

    public void deleteCase(long idtest_cases){
        testList.removeIf(t ->t.getIdtest_cases()==idtest_cases);
    }

    public Optional <TestCase> findById(long idtest_cases){
        return testList.stream().filter(t -> t.getIdtest_cases()==idtest_cases).findFirst();
    }

    public void updateCaseUser(TestCase updatedTestCase, List<Integer> userID) {
        Optional<TestCase> existingTestCaseOpt = findById(updatedTestCase.getIdtest_cases());
        if (existingTestCaseOpt.isPresent()) {
            TestCase existingTestCase = existingTestCaseOpt.get();
            existingTestCase.setUserID(userID);
            existingTestCase.setStatus(updatedTestCase.getStatus());
            // Update other fields as necessary
            updateCase(existingTestCase);
        } else {
            throw new NoSuchElementException("Test case not found with ID: " + updatedTestCase.getIdtest_cases());
        }
    }

    @Scheduled(cron = "0 0 0 * * ?") // Run every day at midnight
    public void sendDeadlineNotifications() {
        LocalDate today = LocalDate.now();

        for (TestCase testCase : testList) {
            LocalDate deadlineDate = LocalDate.parse(testCase.getDeadline());
            int daysUntilDeadline = (int) today.until(deadlineDate).getDays();

            if (daysUntilDeadline <= 7 && daysUntilDeadline >= 0) {
                sendNotificationForTestCase(testCase);
            }
        }
    }

        private void sendNotificationForTestCase(TestCase testCase) {
        List<String> userEmails = testCase.getUserID().stream()
                .map(userId -> {
                    ManageUser user = ManageUserService.getUserById(userId);
                    return (user != null) ? user.getEmail() : "";
                })
                .filter(email -> !email.isEmpty())
                .collect(Collectors.toList());

        if (!userEmails.isEmpty()) {
            String subject = "Test Case Deadline Notification";
            String message = String.format("Dear user, the deadline for test case '%s' is approaching. Please review it.", testCase.getTestCaseName());

            for (String userEmail : userEmails) {
                mailService.sendMail(userEmail, new MailStructure(userEmail, subject, message));
            }
        }
    }

    public TestCase getTestCaseById(long idtest_cases) {

     return testList.stream()
                       .filter(testCase -> testCase.getIdtest_cases() == idtest_cases)
                       .findFirst()
                       .orElseThrow(() -> new NoSuchElementException("Test case not found"));
    }

    public void updateCase(TestCase testCase){
        deleteCase(testCase.getIdtest_cases());
        testList.add(testCase);
    }

    public void changeStatus(long idtest_cases, String newStatus) {
        Optional<TestCase> testCaseOptional = findById(idtest_cases);
        testCaseOptional.ifPresent(testCase -> {
            testCase.setStatus(newStatus);
            updateCase(testCase);
        });
    }

    public void setUnderReview(long idtest_cases) {
        changeStatus(idtest_cases, "Under Review");
    }

    public void setNeedsRevision(long idtest_cases) {
        changeStatus(idtest_cases, "Needs Revision");
    }
}
