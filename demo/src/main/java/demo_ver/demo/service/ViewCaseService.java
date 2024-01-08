package demo_ver.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import demo_ver.demo.model.ManageUser;
import demo_ver.demo.model.TestCase;
import demo_ver.demo.mail.MailService;
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

    //check deadline
        public void checkDeadlineAndSendNotification(TestCase testCase, MailService mailService) {
        if ("PENDING_APPROVAL".equals(testCase.getStatus())) {
            // Adjust the condition based on your actual status values
            // Assuming the deadline is in the format "yyyy-MM-dd"
            LocalDate current = LocalDate.now();
            LocalDate deadlineDate = LocalDate.parse(testCase.getDeadline());

            if (current.isAfter(deadlineDate)) {
                // Deadline has been reached, send notification
                sendNotificationEmail(testCase, mailService);
            }
        }
    }

    //send notification email
        private void sendNotificationEmail(TestCase testCase, MailService mailService) {
        List<String> usernames = testCase.getUsername();
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


        public List<TestCase> getPendingTestCasesForUser(int userID) {
            return testList.stream()
            .filter(testCase -> testCase.getUserID().contains(userID) && "PENDING_APPROVAL".equals(testCase.getStatus()))
            .collect(Collectors.toList());
        }
}
