package demo_ver.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewTestCaseController {
    
    @GetMapping("/add")
    public String showAddTestCaseForm() {
        return "addTestCase"; // Thymeleaf template name for the addTestCase.html file
    }  
}
