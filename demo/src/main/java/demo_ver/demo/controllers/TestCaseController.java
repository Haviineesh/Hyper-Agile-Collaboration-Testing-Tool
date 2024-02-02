package demo_ver.demo.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import java.security.Principal; // Import Principal for getting logged-in user's information
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import demo_ver.demo.model.ManageUser;
import demo_ver.demo.model.TestCase;
import demo_ver.demo.service.ManageRoleService;
import demo_ver.demo.service.ManageUserService;
import demo_ver.demo.service.ViewCaseService;

@Controller
public class TestCaseController {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private ViewCaseService viewCaseService;

   @GetMapping("/view")
public String viewCase(Model model) {
    List<TestCase> testCases = ViewCaseService.findAllList();

    // Assuming ManageUserService.getAllUsers() returns a List<ManageUser>
    List<ManageUser> allUsers = ManageUserService.getAllUsers();

    // Set username for each test case
    for (TestCase testCase : testCases) {
        List<Integer> userIds = testCase.getUserID();
        List<String> usernames = userIds.stream()
                .map(userId -> {
                    ManageUser user = ManageUserService.getUserById(userId);
                    return (user != null) ? user.getUsername() : "";
                })
                .collect(Collectors.toList());

        // Assuming you want to concatenate usernames into a single string
        testCase.setUsername(String.join(", ", usernames));
    }

    model.addAttribute("testCase", testCases);
    model.addAttribute("users1", allUsers);
    return "viewTestCase";
}

    

    @GetMapping("/add")
    public String showAddTestCaseForm(Model model) {
        model.addAttribute("testCase", new TestCase());
        model.addAttribute("users", ManageUserService.getAllUsers());
        return "addTestCase";
    }

    @PostMapping("/save")
    public String addTestCaseForm(TestCase testCase, @RequestParam("userID") List<Integer> userID, Model model) {
        model.addAttribute("tests", ViewCaseService.findAllList());

         // Check if the test case name already exists
        if (viewCaseService.istestCaseExists(testCase.getTestCaseName())) {
            model.addAttribute("testCaseNameExists", true);
            return "addTestCase";
        }
        // Check if the deadline is later than the date created
        if (!isDeadlineLaterThanDateCreated(testCase.getDateCreated(), testCase.getDeadline())) {
            model.addAttribute("deadlineInvalid", true);
            return "addTestCase";
        }

        // Proceed with adding the test case
        viewCaseService.addTestCaseForm(testCase, userID);
        return "redirect:/view";
    }

    // Helper method to check if deadline is later than date created
    private boolean isDeadlineLaterThanDateCreated(String dateCreated, String deadline) {
        LocalDate createdDate = LocalDate.parse(dateCreated);
        LocalDate deadlineDate = LocalDate.parse(deadline);
        return deadlineDate.isAfter(createdDate);
    }

    @GetMapping("/deleteCase/{idtest_cases}")
    public String deleteCase(@PathVariable("idtest_cases") Long idtest_cases) {
        viewCaseService.deleteCase(idtest_cases);
        return "redirect:/view";
    }

    @GetMapping("/editCase/{idtest_cases}")
    public String editCase(@PathVariable("idtest_cases") Long idtest_cases, Model model) {
        TestCase testCaseToEdit = viewCaseService.getTestCaseById(idtest_cases);
        model.addAttribute("testCase", testCaseToEdit);
        model.addAttribute("users", ManageUserService.getAllUsers()); // Add users for assigning to the test case
        return "EditTestCase"; // The name of the edit form template
    }

    @PostMapping("/update")
    public String editTestCaseForm(TestCase testCase, @RequestParam("userID") List<Integer> userID, Model model) {

        model.addAttribute("tests", ViewCaseService.findAllList());
        // if (viewCaseService.istestCaseExists(testCase.getTestCaseName())) {
        //     model.addAttribute("testCaseNameExists", true);
        //     return "EditTestCase";
        // }
        // Check if the deadline is later than the date created
        if (!isDeadlineLaterThanDateCreated(testCase.getDateCreated(), testCase.getDeadline())) {
            model.addAttribute("deadlineInvalid", true);
            return "addTestCase";
        }
        viewCaseService.updateCaseUser(testCase, userID);
        return "redirect:/view";
    }

    @PostMapping("/setUserStatus")
    public String setUserStatus(@RequestParam Long testCaseId, @RequestParam String status, Principal principal) {
        String username = principal.getName(); // Get logged-in username
        viewCaseService.setUserStatusForTestCase(testCaseId, username, status);
        return "redirect:/view";
    }
}
