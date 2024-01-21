package demo_ver.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo_ver.demo.model.ManageUser;
import demo_ver.demo.model.TestCase;
import demo_ver.demo.mail.MailService;
import demo_ver.demo.utils.RandomNumber;

@Service
public class ViewCaseService {

    private static List<TestCase> testList = new ArrayList<TestCase>() {
        {
            add(new TestCase(RandomNumber.getRandom(100,999), "001", 8, "Diagram", "desc1", "2023-12-11", "2023-11-10", Arrays.asList(2000)));
            add(new TestCase(RandomNumber.getRandom(100,999), "002", 15, "Package", "desc23", "2023-11-07", "2023-11-17", Arrays.asList(2001)));
            add(new TestCase(RandomNumber.getRandom(100,999), "003", 17, "Behavioral", "desc34", "2023-12-05", "2023-11-15", Arrays.asList(2001)));
            add(new TestCase(RandomNumber.getRandom(100,999), "004", 19, "Diagram", "desc56", "2023-12-20", "2024-01-07", Arrays.asList(2002)));
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

        sendAssignmentNotification(testCase);
        scheduleDeadlineNotification(testCase);
    }

    private void sendAssignmentNotification(TestCase testCase) {
        List<Integer> assignedUserIDs = testCase.getUserID();
        for (Integer userID : assignedUserIDs) {
            ManageUser user = ManageUserService.getUserById(userID);
            if (user != null && user.getEmail() != null) {
                String userEmail = user.getEmail();
                String subject = "New Test Case Assignment";
                String message = "Dear user, you have been assigned a new test case. Details:\n" +
                        "Test Case ID: " + testCase.getIdtest_cases() + "\n" +
                        "Test Case Name: " + testCase.getTestCaseName() + "\n" +
                        "Deadline: " + testCase.getDeadline() + "\n" +
                        "Please review and approve the test case before the deadline.";
                mailService.sendAssignedMail(userEmail, subject, message);
            }
        }
    }

    private void scheduleDeadlineNotification(TestCase testCase) {
        LocalDateTime deadlineDateTime = LocalDate.parse(testCase.getDeadline()).atStartOfDay();
        long initialDelay = ChronoUnit.SECONDS.between(LocalDateTime.now(), deadlineDateTime);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleWithFixedDelay(() -> sendDeadlineNotification(testCase), initialDelay, 24 * 60 * 60, TimeUnit.SECONDS);
        executorService.schedule(() -> executorService.shutdown(), initialDelay + 24 * 60 * 60, TimeUnit.SECONDS);
    }

    private void sendDeadlineNotification(TestCase testCase) {
        List<Integer> assignedUserIDs = testCase.getUserID();
        for (Integer userID : assignedUserIDs) {
            ManageUser user = ManageUserService.getUserById(userID);
            if (user != null && user.getEmail() != null) {
                String userEmail = user.getEmail();
                String subject = "Test Case Deadline Notification";
                String message = "Dear user, the deadline for the assigned test case has been reached. Details:\n" +
                        "Test Case ID: " + testCase.getIdtest_cases() + "\n" +
                        "Test Case Name: " + testCase.getTestCaseName() + "\n" +
                        "Deadline: " + testCase.getDeadline() + "\n" +
                        "Please ensure that the test case is completed.";
                mailService.sendAssignedMail(userEmail, subject, message);
            }
        }
    }

    public void setUserStatusForTestCase(Long testCaseId, String username, String status) {
        Optional<TestCase> testCaseOptional = findById(testCaseId);
        if (testCaseOptional.isPresent()) {
            TestCase testCase = testCaseOptional.get();
            testCase.setUserStatus(username, status);
            updateCase(testCase);
        } else {
            throw new NoSuchElementException("Test case not found with ID: " + testCaseId);
        }
    }

    private Optional<TestCase> findById(long idtest_cases) {
        return testList.stream()
                       .filter(t -> t.getIdtest_cases() == idtest_cases)
                       .findFirst();
    }

    public void updateCaseUser(TestCase updatedTestCase, List<Integer> userID) {
        Optional<TestCase> existingTestCaseOpt = findById(updatedTestCase.getIdtest_cases());
        if (existingTestCaseOpt.isPresent()) {
            TestCase existingTestCase = existingTestCaseOpt.get();
            existingTestCase.setUserID(userID);
            // Update other fields of the TestCase as necessary
            existingTestCase.setTest_desc(updatedTestCase.getTest_desc());
            existingTestCase.setDeadline(updatedTestCase.getDeadline());
            // ... other fields ...
            updateCase(existingTestCase); // Update the case in the list
        } else {
            throw new NoSuchElementException("Test case not found with ID: " + updatedTestCase.getIdtest_cases());
        }
    }

    private void updateCase(TestCase testCase) {
        deleteCase(testCase.getIdtest_cases());
        testList.add(testCase);
    }

    public void deleteCase(long idtest_cases) {
        testList.removeIf(t -> t.getIdtest_cases() == idtest_cases);
    }

    // In ViewCaseService class

public TestCase getTestCaseById(Long idtest_cases) {
    return testList.stream()
                   .filter(testCase -> testCase.getIdtest_cases().equals(idtest_cases))
                   .findFirst()
                   .orElseThrow(() -> new NoSuchElementException("Test case not found with ID: " + idtest_cases));
}


    // ... other existing methods ...
}
