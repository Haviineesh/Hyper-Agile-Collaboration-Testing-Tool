package demo_ver.demo.model;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class TestCase {
    private int idtest_cases;
    private String test_desc;
    private String approvalStatus;

     // Default constructor (no-argument constructor)

    public int getIdtest_cases() {
        return idtest_cases;
    }
    public void setIdtest_cases(int idtest_cases) {
        this.idtest_cases = idtest_cases;
    }
    public String getTest_desc() {
        return test_desc;
    }
    public void setTest_desc(String test_desc) {
        this.test_desc = test_desc;
    }
    public TestCase(int idtest_cases,String test_desc, String approvalStatus) {
        this.idtest_cases = idtest_cases;
        this.test_desc = test_desc;
        this.approvalStatus = approvalStatus;
    }
    public String getApprovalStatus() {
        return approvalStatus;
    }

    public TestCase(){

    }

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
    public void setApprovalStatus(String string) {
    }


}


    
    

