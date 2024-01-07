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

    @GetMapping("/testcases/details/{idtest_cases}")
    public String viewTestCaseDetails(@PathVariable("idtest_cases") Long idtest_cases, Model model) {
        // Fetch the TestCase by id
        TestCase testCase = viewCaseService.getTestCaseById(idtest_cases);

        // Add the TestCase to the model
        model.addAttribute("testCase", testCase);

        // Return the Thymeleaf template name for the details view
        return "viewTestCasesDetails";
    }

    @GetMapping("/testcases/approveStatus/{idtest_cases}")
    public String approveTestCase(@PathVariable("idtest_cases") Long idtest_cases) {
        viewCaseService.changeStatus(idtest_cases, "Approved");
        return "redirect:/view";
    }

    @GetMapping("/testcases/rejectStatus/{idtest_cases}")
    public String rejectTestCase(@PathVariable("idtest_cases") Long idtest_cases) {
        viewCaseService.changeStatus(idtest_cases, "Rejected");
        return "redirect:/view";
    }

    @GetMapping("/testcases/setUnderReview/{idtest_cases}")
public String setUnderReview(@PathVariable("idtest_cases") Long idtest_cases) {
    viewCaseService.setUnderReview(idtest_cases);
    return "redirect:/view";
}

@GetMapping("/testcases/setNeedsRevision/{idtest_cases}")
public String setNeedsRevision(@PathVariable("idtest_cases") Long idtest_cases) {
    viewCaseService.setNeedsRevision(idtest_cases);
    return "redirect:/view";
}

}



