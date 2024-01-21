package demo_ver.demo.controllers;

import java.util.List;
import java.security.Principal; // Import Principal for getting logged-in user's information

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import demo_ver.demo.model.TestCase;
import demo_ver.demo.service.ManageUserService;
import demo_ver.demo.service.ViewCaseService;

@Controller
public class TestCaseController {

    @Autowired
    private ViewCaseService viewCaseService;

    @GetMapping("/view")
    public String viewCase(Model model) {
        model.addAttribute("testCase", ViewCaseService.findAllList());
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
        viewCaseService.addTestCaseForm(testCase, userID);
        return "redirect:/view";
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
