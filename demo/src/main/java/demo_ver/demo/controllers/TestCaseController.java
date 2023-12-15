package demo_ver.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import demo_ver.demo.model.TestCase;
import demo_ver.demo.service.ViewCaseService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class TestCaseController {

    @Autowired
    private ViewCaseService viewCaseService;


    @GetMapping("/view")
    public String viewCase(Model model) {
        // List<TestCase> cases = viewCaseService.findAllList();
        model.addAttribute("testCase", ViewCaseService.findAllList());
        return "viewTestCase";
    }

    // @GetMapping("/view")
    // @ResponseBody
    // public List<TestCase> getAllRoles(){
    //     return ViewCaseService.getAllRoles();
    // }

    @GetMapping("/add")
    public String showAddTestCaseForm(Model model) {
        model.addAttribute("testCase", new TestCase());
        return "addTestCase"; // Thymeleaf template name for the addTestCase.html file
    }  

    @PostMapping("/save")
	public String addTestCaseForm(TestCase testCase,Model model) {
		viewCaseService.addTestCaseForm(testCase);// save product into database, using DbService
		return "redirect:/view";
	}

    @GetMapping("/deleteCase/{idtest_cases}")
	public String deleteCase(@PathVariable("idtest_cases") Long idtest_cases) {
		
       viewCaseService.deleteCase(idtest_cases);
		return "redirect:/view";
	}

    @GetMapping("/editCase/{idtest_cases}")
	public String editCase(@PathVariable("idtest_cases") Long idtest_cases,Model model) {
		
        model.addAttribute("testCase", viewCaseService.findById(idtest_cases));
		return "EditTestCase";
	}

    @PostMapping("/update")
	public String editTestCaseForm(TestCase testCase,Model model) {
		
        viewCaseService.updateCase(testCase);
		return "redirect:/view";
	}

    
}
