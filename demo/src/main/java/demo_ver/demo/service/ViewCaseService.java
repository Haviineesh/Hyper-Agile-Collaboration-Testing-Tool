package demo_ver.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


import org.springframework.stereotype.Service;

import demo_ver.demo.model.TestCase;

@Service
public class ViewCaseService {

     private static List<TestCase> testList = new ArrayList<TestCase>(Arrays.asList(
            new TestCase(234,"Tester", "Approved"),
            new TestCase(334,"Proproduction", "Pending"),
            new TestCase(22, "Manager", "Rejected")));

    public static List<TestCase> getAllRoles() {
        return new ArrayList<TestCase>(testList);
    }

    public void addTestCaseForm(TestCase testCase) {
        // Add the product to the static productList
        testList.add(testCase);
    }

    public String getApprovalStatus(int idtest_cases) {
        for (TestCase testCase : testList) {
            if (testCase.getIdtest_cases() == idtest_cases) {
                return testCase.getApprovalStatus();
            }
        }
        return "Not Found";
    }

    // public static void deleteById(int idtest_cases) {
    //     testList.removeIf(testCase -> testCase.getId() == idtest_cases);
    // }

    // public boolean deleteCase(String name) {
	//     Iterator<TestCase> iterator = testList.iterator();
	//     while (iterator.hasNext()) {
	//         TestCase testCase = iterator.next();
	//         if (testCase.getName().equalsIgnoreCase(name)) {
	//             iterator.remove();
	//             return true; // Product found and deleted
	//         }
	//     }
	//     return false; // Product not found
	// }
}
