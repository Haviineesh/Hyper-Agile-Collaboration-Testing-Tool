package demo_ver.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import demo_ver.demo.model.TestCase;

@Service
public class ViewCaseService {

    private List<TestCase> testList = new ArrayList<>(Arrays.asList(
        new TestCase(234, "Tester", "24/02/2024", "12/12/2023", "234", "reason1", "pending", "UserManagement"),
        new TestCase(334, "Proproduction", "01/01/2024", "10/12/2023", "334", "reason2", "need verification", "UserData"),
        new TestCase(22, "Manager", "27/12/2023", "13/12/2023", "22", "reason3", "rejected", "TestCaseDetails")));

    public List<TestCase> getAllRoles() {
        return new ArrayList<>(testList);
    }

    public void addTestCaseForm(TestCase testCase) {
        testList.add(testCase);
    }

    public TestCase getTestCaseById(int id) {

     return testList.stream()
                       .filter(testCase -> testCase.getIdtest_cases() == id)
                       .findFirst()
                       .orElseThrow(() -> new NoSuchElementException("Test case not found"));
    }
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
