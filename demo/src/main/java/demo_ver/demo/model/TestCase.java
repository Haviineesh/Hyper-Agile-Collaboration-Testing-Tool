package demo_ver.demo.model;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class TestCase {
    private Long idtest_cases;
    private String test_desc;
    private String code;
    private String deadline;
    private String dateUpdated;
    private String projectId;
    private String reason;
    private String status;
    private String testCaseName;


    public TestCase(){

    }

    public TestCase(Long idtest_cases, String code, String test_desc) {
        this.idtest_cases = idtest_cases;
        this.test_desc = test_desc;
        this.code = code;
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
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
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


}






    
    

