package demo_ver.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import demo_ver.demo.model.TestCase;
import demo_ver.demo.utils.RandomNumber;




@Service
public class ViewCaseService {

     private static List<TestCase> testList = new ArrayList<TestCase>(){
     {
            add(new TestCase(RandomNumber.getRandom(100,999),"001",8,"Diagram","desc1","2023-12-11","2023-11-10","Pending",Arrays.asList(2000)));
            add(new TestCase(RandomNumber.getRandom(100,999),"002",15,"Package","desc23","2023-11-07","2023-11-17","Pending",Arrays.asList(2001)));
            add(new TestCase(RandomNumber.getRandom(100,999),"003",17,"Behavorial","desc34","2023-12-05","2023-11-15","Pending",Arrays.asList(2001)));
     }
    };
           

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



}
