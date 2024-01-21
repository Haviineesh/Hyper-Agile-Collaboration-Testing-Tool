package demo_ver.demo.controllers;

import java.security.Principal;

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
        TestCase testCase = viewCaseService.getTestCaseById(idtest_cases);
        model.addAttribute("testCase", testCase);
        return "viewTestCasesDetails";
    }

    @GetMapping("/testcases/approveStatus/{idtest_cases}")
    public String approveTestCase(@PathVariable("idtest_cases") Long idtest_cases, Principal principal) {
        String username = principal.getName(); 
        viewCaseService.setUserStatusForTestCase(idtest_cases, username, "Approved");
        return "redirect:/view";
    }

    @GetMapping("/testcases/rejectStatus/{idtest_cases}")
    public String rejectTestCase(@PathVariable("idtest_cases") Long idtest_cases, Principal principal) {
        String username = principal.getName(); 
        viewCaseService.setUserStatusForTestCase(idtest_cases, username, "Rejected");
        return "redirect:/view";
    }

    @GetMapping("/testcases/needsRevisionStatus/{idtest_cases}")
    public String needsRevisionTestCase(@PathVariable("idtest_cases") Long idtest_cases, Principal principal) {
        String username = principal.getName(); 
        viewCaseService.setUserStatusForTestCase(idtest_cases, username, "Needs Revision");
        return "redirect:/view";
    }

    @GetMapping("/testcases/underReviewStatus/{idtest_cases}")
    public String underReviewTestCase(@PathVariable("idtest_cases") Long idtest_cases, Principal principal) {
        String username = principal.getName(); 
        viewCaseService.setUserStatusForTestCase(idtest_cases, username, "Under Review");
        return "redirect:/view";
    }
}
