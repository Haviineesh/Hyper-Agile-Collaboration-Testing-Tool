package demo_ver.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo_ver.demo.model.TestCase;
import demo_ver.demo.service.ManageUserService;
import demo_ver.demo.service.ViewCaseService;


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
        model.addAttribute("users", ManageUserService.getAllUsers());
        return "addTestCase"; // Thymeleaf template name for the addTestCase.html file
    }  

    @PostMapping("/save")
	public String addTestCaseForm(TestCase testCase,@RequestParam("userID") List<Integer> userID,Model model) {
		viewCaseService.addTestCaseForm(testCase,userID);// save product into database, using DbService
		return "redirect:/view";
	}

    @GetMapping("/deleteCase/{idtest_cases}")
	public String deleteCase(@PathVariable("idtest_cases") Long idtest_cases) {
		
       viewCaseService.deleteCase(idtest_cases);
		return "redirect:/view";
	}

    @GetMapping("/editCase/{idtest_cases}")
	public String editCase(@PathVariable("idtest_cases") Long idtest_cases,Model model) {
		
        TestCase userToAssign = viewCaseService.getTestCaseById(idtest_cases);
        model.addAttribute("testCase", userToAssign);
        model.addAttribute("users", ManageUserService.getAllUsers());
		return "EditTestCase";
	}

    @PostMapping("/update")
	public String editTestCaseForm(TestCase testCase,@RequestParam("userID") List<Integer>  userID,Model model) {
		
        viewCaseService.updateCaseUser(testCase,userID);
		return "redirect:/view";
	}

    
}
