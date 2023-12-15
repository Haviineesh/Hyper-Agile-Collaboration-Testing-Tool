package demo_ver.demo.model;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class TestCase {
    private int idtest_cases;
    private String test_desc;
    private String deadline;
    private String dateUpdated;
    private String projectId;
    private String reason;
    private String status;
    private String testCaseName;

    public TestCase() { }

    public TestCase(int idtest_cases, String test_desc, String deadline, String dateUpdated, String projectId, String reason, String status, String testCaseName) {
        this.idtest_cases = idtest_cases;
        this.test_desc = test_desc;
        this.deadline = deadline;
        this.dateUpdated = dateUpdated;
        this.projectId = projectId;
        this.reason = reason;
        this.status = status;
        this.testCaseName = testCaseName;
    }

    // Getters and Setters
    public int getIdtest_cases() { return idtest_cases; }
    public void setIdtest_cases(int idtest_cases) { this.idtest_cases = idtest_cases; }

    public String getTest_desc() { return test_desc; }
    public void setTest_desc(String test_desc) { this.test_desc = test_desc; }

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


    
    class testRepo {

        public static Optional<TestCase> findById(int idtest_cases) {
            return null;
        }

        public static void deleteById(int idtest_cases) {
        }

        public static List<TestCase> findAll() {
            return null;
        }

    }


    public List<TestCase> viewList() {
        // Implementation for viewing a list of roles
        // Retrieve the list of roles from the database (assuming you have a repository)
        // List<ManageRole> roles = roleRepository.findAll();

        // You may also add additional logic to filter or customize the list as needed
    List<TestCase> test = testRepo.findAll();

    return test;
    }

    public String addNewCase(int idtest_cases,String test_desc) {
        // Implementation for adding a new role
        TestCase newCase = new TestCase();
        newCase.setIdtest_cases(idtest_cases);
        newCase.setTest_desc(test_desc);

        // Save the new role to the database (assuming you have a repository)
        // roleRepository.save(newRole);

        return "New role added";
    }

    public String deleteCase(int idtest_cases) {
        // Implementation for deleting a role
        // Delete the role from the database (assuming you have a repository)
        Optional<TestCase> existingCaseOptional = testRepo.findById(idtest_cases);

        // Check if the role exists
        if (existingCaseOptional.isPresent()) {
            // Delete the role from the database
            testRepo.deleteById(idtest_cases);

            return "Role deleted successfully";
        } else {
            return "Role not found";
        }
    }


}






    
    

