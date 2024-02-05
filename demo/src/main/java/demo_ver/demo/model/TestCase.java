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
    private String overallStatus;
    private String username;
    private String createdBy;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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


        // Method to determine overall status based on user statuses 
       // METHOD 1
        // public String determineOverallStatus() {
        //     if (userStatuses.containsValue("Rejected")) {
        //         return "Rejected";
        //     } else if (userStatuses.values().stream().allMatch(status -> status.equals("Approved"))) {
        //         return "Approved";
        //     } else if (userStatuses.values().stream().anyMatch(status -> status.equals("Under Review") || status.equals("Needs Revision"))) {
        //         return "Pending";
        //     } else {
        //         return "Pending"; // Default to Pending if none of the above conditions are met
        //     }
        // }

        public String determineOverallStatus() {
            // If any user has rejected the test case, then the overall status is "Rejected"
            if (userStatuses.containsValue("Rejected")) {
                return "Rejected";
            }
            
            // Check if all assigned users have set their status
            // Only change the overall status if all users have set their status
            if (userStatuses.size() == userID.size()) {
                // If all users have approved, then the overall status is "Approved"
                boolean allApproved = userStatuses.values().stream().allMatch(status -> status.equals("Approved"));
                if (allApproved) {
                    return "Approved";
                }
        
                // If all users have "Needs Revision", then the overall status is "Needs Revision"
                if (userStatuses.values().stream().allMatch(status -> status.equals("Needs Revision"))) {
                    return "Needs Revision";
                }
        
                // If any user has set "Under Review" or "Needs Revision" without any "Reject", then it's "Pending"
                boolean anyUnderReviewOrNeedsRevision = userStatuses.values().stream()
                        .anyMatch(status -> status.equals("Under Review") || status.equals("Needs Revision"));
                if (anyUnderReviewOrNeedsRevision) {
                    return "Pending";
                }
            }
            
            // Default to "Pending" if not all users have set their status or if none of the above conditions are met
            return "Pending";
        }
        

        public String getOverallStatus() {
            return overallStatus;
        }
    
        public void setOverallStatus(String overallStatus) {
            this.overallStatus = overallStatus;
        }
    }
    