package demo_ver.demo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import demo_ver.demo.service.ManageUserService;

@EntityScan
public class TestCase {
    private Long idtest_cases;
    private String test_desc;
    private String deadline;
    private String dateUpdated;
    private String projectId;
    private String reason;
    private String testCaseName;
    private String dateCreated;
    private int smartContractID;
    private List<Integer> userID;
    private Map<String, String> userStatuses = new HashMap<>(); // New field for user-specific statuses

    public TestCase() {
        // Default constructor
    }

    public TestCase(Long idtest_cases, String projectId, int smartContractID, String testCaseName, String test_desc, String dateCreated, String deadline, List<Integer> userID) {
        this.idtest_cases = idtest_cases;
        this.projectId = projectId;
        this.smartContractID = smartContractID;
        this.testCaseName = testCaseName;
        this.test_desc = test_desc;
        this.dateCreated = dateCreated;
        this.deadline = deadline;
        this.userID = userID;
    }

    // Getters and setters for existing fields
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getSmartContractID() {
        return smartContractID;
    }

    public void setSmartContractID(int smartContractID) {
        this.smartContractID = smartContractID;
    }

    public List<Integer> getUserID() {
        return userID;
    }

    public void setUserID(List<Integer> userID) {
        this.userID = userID;
    }

    // Methods for user statuses
    public Map<String, String> getUserStatuses() {
        return userStatuses;
    }

    public void setUserStatus(String username, String status) {
        userStatuses.put(username, status);
    }

    // Method to get usernames of assigned users
    public List<String> getUsernames() {
        return userID.stream()
                .map(userId -> {
                    ManageUser user = ManageUserService.getUserById(userId);
                    return (user != null) ? user.getUsername() : "";
                })
                .collect(Collectors.toList());
    }
}
