package demo_ver.demo.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo_ver.demo.mail.MailService;
import demo_ver.demo.model.ManageUser;
import demo_ver.demo.model.TestCase;
import demo_ver.demo.utils.RandomNumber;

@Service
public class ViewCaseService {
    private static final Logger logger = LoggerFactory.getLogger(ViewCaseService.class);
    private static List<TestCase> testList = new ArrayList<TestCase>() {
        {
            add(new TestCase("", RandomNumber.getRandom(100, 999), "002", "15", "Package", "desc23", "2023-11-07",
                    "2023-11-17", Arrays.asList(2001)));
            add(new TestCase("", RandomNumber.getRandom(100, 999), "003", "17", "Behavioral", "desc34", "2023-12-05",
                    "2023-11-15", Arrays.asList(2001)));
            add(new TestCase("", RandomNumber.getRandom(100, 999), "004", "19", "Diagram", "desc56", "2023-12-20",
                    "2024-01-07", Arrays.asList(2002)));
        }
    };

    @Autowired
    private MailService mailService;

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    private static final String HYPERLEDGER_BASE_URL = "http://localhost:8090/api"; // Use ngrok link here instead

    // get all test cases
    // public static List<TestCase> findAllList() {
    // String url = HYPERLEDGER_BASE_URL + "/getAllTestCases";
    // HttpRequest request = HttpRequest.newBuilder()
    // .uri(URI.create(url))
    // .GET()
    // .build();

    // try { // comment this try catch block to view the old test cases
    // logger.info("Sending request to URL: {}", url);
    // HttpResponse<String> response = httpClient.send(request,
    // HttpResponse.BodyHandlers.ofString());
    // logger.info("Received response with status code: {}", response.statusCode());
    // if (response.statusCode() == 200) {
    // ObjectMapper objectMapper = new ObjectMapper();
    // TestCase[] testCases = objectMapper.readValue(response.body(),
    // TestCase[].class);
    // testList = Arrays.asList(testCases);
    // } else {
    // throw new RuntimeException("Failed to fetch test cases, status code: " +
    // response.statusCode());
    // }
    // } catch (JsonParseException e) {
    // logger.error("JSON parsing error while fetching test cases: {}",
    // e.getMessage());
    // throw new RuntimeException("JSON parsing error while fetching test cases",
    // e);
    // } catch (JsonMappingException e) {
    // logger.error("JSON mapping error while converting test cases: {}",
    // e.getMessage());
    // throw new RuntimeException("JSON mapping error while converting test cases",
    // e);
    // } catch (IOException e) {
    // logger.error("IO error while fetching test cases: {}", e.getMessage());
    // logger.debug("IOException details: ", e); // This will print the stack trace
    // throw new RuntimeException("IO error while fetching test cases", e);
    // } catch (InterruptedException e) {
    // logger.error("Request interrupted while fetching test cases: {}",
    // e.getMessage());
    // Thread.currentThread().interrupt();
    // throw new RuntimeException("Request interrupted while fetching test cases",
    // e);
    // }
    // return testList;

    // // 1. Call Hyperledger api instead -> getAllTestCase (GET method)

    // // 2. assign api response to testList
    // }

    // Get all test cases API By Havi
    public static List<TestCase> findAllList() {
        String url = HYPERLEDGER_BASE_URL + "/getAllTestCases";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        List<TestCase> testCases = new ArrayList<>();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode messageNode = rootNode.path("message");

            if (messageNode.isArray()) {
                for (JsonNode node : messageNode) {
                    TestCase testCase = new TestCase(
                            node.path("status").asText(),
                            node.path("idtest_cases").asLong(),
                            node.path("projectId").asText(),
                            node.path("smartContractID").asText(),
                            node.path("testCaseName").asText(),
                            node.path("test_desc").asText(),
                            node.path("dateCreated").asText(),
                            node.path("deadline").asText(),
                            new ArrayList<>() // Assuming userID is a List<Integer>
                    );
                    testCases.add(testCase);
                }
            }
        } catch (Exception e) {
            logger.error("Error fetching test cases: ", e);
        }

        return testCases;
    }

    public void addTestCaseForm(TestCase testCase, List<Integer> userID) {
        testCase.setIdtest_cases(RandomNumber.getRandom(0, 20));
        testCase.setUserID(userID);
        testList.add(testCase);
        // 1. hyperledger call to addTestCase (POST method)
        // 2. assign api response to testCase
        // 3. testList.add(testCase)

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
        executorService.scheduleWithFixedDelay(() -> sendDeadlineNotification(testCase), initialDelay, 24 * 60 * 60,
                TimeUnit.SECONDS);
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
            String overallStatus = testCase.determineOverallStatus(); // Determine the overall status
            // Assuming you have a method setOverallStatus in your TestCase model
            testCase.setOverallStatus(overallStatus); // Update the overall status
            updateCase(testCase);
        } else {
            throw new NoSuchElementException("Test case not found with ID: " + testCaseId);
        }
    }

    private Optional<TestCase> findById(Long idtest_cases) {
        return testList.stream()
                .filter(t -> t.getIdtest_cases() == idtest_cases)
                .findFirst();
    }

    public void updateCaseUser(TestCase updatedTestCase, List<Integer> userID) {
        Optional<TestCase> existingTestCaseOpt = findById(updatedTestCase.getIdtest_cases());
        if (existingTestCaseOpt.isPresent()) {
            TestCase existingTestCase = existingTestCaseOpt.get();
            existingTestCase.setProjectId(updatedTestCase.getProjectId());
            existingTestCase.setSmartContractID(updatedTestCase.getSmartContractID());
            existingTestCase.setTestCaseName(updatedTestCase.getTestCaseName());
            existingTestCase.setTest_desc(updatedTestCase.getTest_desc());
            existingTestCase.setDateCreated(updatedTestCase.getDateCreated());
            existingTestCase.setDeadline(updatedTestCase.getDeadline());
            existingTestCase.setUserID(userID);
            // Here, you might also want to update the user statuses if necessary
            // existingTestCase.setUserStatuses(updatedTestCase.getUserStatuses());

            String overallStatus = existingTestCase.determineOverallStatus(); // Recalculate overall status
            existingTestCase.setOverallStatus(overallStatus);

            // No need to call deleteCase; just update the object directly in the list
            // updateCase(existingTestCase); // This method might be redundant
        } else {
            throw new NoSuchElementException("Test case not found with ID: " + updatedTestCase.getIdtest_cases());
        }
    }

    private void updateCase(TestCase testCase) {
        deleteCase(testCase.getIdtest_cases());
        testList.add(testCase);
    }

    public void deleteCase(Long idtest_cases) {
        testList.removeIf(t -> t.getIdtest_cases() == idtest_cases);
    }

    // Check if a username exists in the system
    public boolean istestCaseExists(String testCaseName) {
        return testList.stream().anyMatch(Test -> Test.getTestCaseName().equalsIgnoreCase(testCaseName));
    }

    // In ViewCaseService class

    public TestCase getTestCaseById(Long idtest_cases) {
        return testList.stream()
                .filter(testCase -> testCase.getIdtest_cases().equals(idtest_cases))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Test case not found with ID: " + idtest_cases));
    }

    public List<TestCase> findTestCasesByUsername(String username) {
        List<TestCase> userTestCases = new ArrayList<>();

        for (TestCase testCase : testList) {
            if (testCase.getUsernames().contains(username)) {
                userTestCases.add(testCase);
            }
        }

        return userTestCases;
    }

    // ... other existing methods ...

}
