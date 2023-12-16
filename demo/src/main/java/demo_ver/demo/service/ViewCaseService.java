package demo_ver.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import demo_ver.demo.model.TestCase;
import demo_ver.demo.utils.RandomNumber;



@Service
public class ViewCaseService {

     private static List<TestCase> testList = new ArrayList<TestCase>(){
     {
            add(new TestCase(RandomNumber.getRandom(100,999),"001",8,"Diagram","desc1","2023-12-11","2023-11-10","Approved"));
            add(new TestCase(RandomNumber.getRandom(100,999),"002",5,"Sequence","desc2","2023-10-10","2023-11-17","Pending"));
            add(new TestCase(RandomNumber.getRandom(100,999),"003",7,"Testing","desc3","2023-12-10","2023-11-15","Disapproved"));
     }
    };
           

    public static List<TestCase> findAllList() {
       return testList;
    }

    public void addTestCaseForm(TestCase testCase) {
        
        testCase.setIdtest_cases(RandomNumber.getRandom(100,999));
        testList.add(testCase);
    }

    public void deleteCase(long idtest_cases){
        testList.removeIf(t ->t.getIdtest_cases()==idtest_cases);
    }

    public Optional <TestCase> findById(long idtest_cases){
        return testList.stream().filter(t -> t.getIdtest_cases()==idtest_cases).findFirst();
    }

    public void updateCase(TestCase testCase){
        deleteCase(testCase.getIdtest_cases());
        testList.add(testCase);
    }

    public TestCase getTestCaseById(long idtest_cases) {

     return testList.stream()
                       .filter(testCase -> testCase.getIdtest_cases() == idtest_cases)
                       .findFirst()
                       .orElseThrow(() -> new NoSuchElementException("Test case not found"));
    }



}
