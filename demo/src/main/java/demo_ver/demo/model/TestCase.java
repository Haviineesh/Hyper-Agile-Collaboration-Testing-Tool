package demo_ver.demo.model;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;

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
    private int smartContractID;


    public TestCase(){

    }

    public TestCase(Long idtest_cases, String projectId,int smartContractID, String testCaseName,String test_desc, String dateCreated, String deadline,
            String status) {
        this.idtest_cases = idtest_cases;
        this.deadline = deadline;
        this.projectId = projectId;
        this.status = status;
        this.testCaseName = testCaseName;
        this.dateCreated = dateCreated;
        this.smartContractID = smartContractID;
        this.test_desc = test_desc;
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


}






    
    

