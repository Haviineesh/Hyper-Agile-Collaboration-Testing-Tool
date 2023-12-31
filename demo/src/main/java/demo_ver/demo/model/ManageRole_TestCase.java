package demo_ver.demo.model;

public class ManageRole_TestCase {
    private int userID;
    private int roleID;
    private Long testCaseID;

    public ManageRole_TestCase(int userID,int roleID, Long testCaseID){
        this.userID = userID;
        this.roleID = roleID;
        this.testCaseID = testCaseID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public Long getTestCaseID() {
        return testCaseID;
    }

    public void setTestCaseID(Long testCaseID) {
        this.testCaseID = testCaseID;
    }

    @Override
    public String toString() {
        return "ManageRole_TestCase [userID=" + userID + ", roleID=" + roleID + ", testCaseID=" + testCaseID + "]";
    }
    
}
