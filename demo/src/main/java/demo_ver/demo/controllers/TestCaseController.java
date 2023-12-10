package demo_ver.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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


    // @GetMapping("/view")
    // public String viewCase(Model model) {
    //     List<TestCase> cases = viewCaseService.getAllRoles();
    //     model.addAttribute("cases", cases);
    //     return "viewTestCase";
    // }

    @GetMapping("/view")
    @ResponseBody
    public List<TestCase> getAllRoles(){
        return ViewCaseService.getAllRoles();
    }

    @GetMapping("/add")
    public String showAddTestCaseForm(Model model) {
        model.addAttribute("testCase", new TestCase());
        return "addTestCase"; // Thymeleaf template name for the addTestCase.html file
    }  

    @PostMapping("/add")
	public String addTestCaseForm(@ModelAttribute("testCase") TestCase testCase) {
		viewCaseService.addTestCaseForm(testCase);// save product into database, using DbService
		return "viewTestCase";
	}

    @GetMapping("/deleteproduct")
	public String deleteProductForm(Model model) {
		// You can include any necessary data for the delete form using model
		return "deleteproductform";
	}

    
}
