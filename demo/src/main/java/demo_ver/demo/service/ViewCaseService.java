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
            add(new TestCase(RandomNumber.getRandom(100,999),"001","Tester"));
            add(new TestCase(RandomNumber.getRandom(100,999),"002","Proproduction"));
            add(new TestCase(RandomNumber.getRandom(100,999), "003","Manager"));
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

    public TestCase getTestCaseById(int id) {

     return testList.stream()
                       .filter(testCase -> testCase.getIdtest_cases() == id)
                       .findFirst()
                       .orElseThrow(() -> new NoSuchElementException("Test case not found"));
    }



}
