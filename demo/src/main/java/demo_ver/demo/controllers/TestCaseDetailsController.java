package demo_ver.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import demo_ver.demo.model.TestCase;
import demo_ver.demo.service.ViewCaseService;

@Controller
public class TestCaseDetailsController {

    @Autowired
    private ViewCaseService viewCaseService;

    @GetMapping("/testcases/details/{id}")
    public String viewTestCaseDetails(@PathVariable("id") int id, Model model) {
        // Fetch the TestCase by id
        TestCase testCase = viewCaseService.getTestCaseById(id);

        // Add the TestCase to the model
        model.addAttribute("testCase", testCase);

        // Return the Thymeleaf template name for the details view
        return "viewTestCasesDetails";
    }
}

