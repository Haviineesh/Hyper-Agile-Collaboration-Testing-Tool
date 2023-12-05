package demo_ver.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import demo_ver.demo.model.TestCase;

@Service
public class ViewCaseService {

     private static List<TestCase> testList = new ArrayList<TestCase>(Arrays.asList(
            new TestCase(234,"Tester"),
            new TestCase(334,"Proproduction"),
            new TestCase(22, "Manager")));

    public static List<TestCase> getAllRoles() {
        return new ArrayList<TestCase>(testList);
    }

    public void addTestCaseForm(TestCase testCase) {
        // Add the product to the static productList
        testList.add(testCase);
    }

    // public static void deleteById(int idtest_cases) {
    //     testList.removeIf(testCase -> testCase.getId() == idtest_cases);
    // }
}
