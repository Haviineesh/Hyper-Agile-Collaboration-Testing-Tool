package demo_ver.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo_ver.demo.model.ViewCase;
import demo_ver.demo.repo.ViewCaseData;


@Controller
public class NewTestCaseController {

    @Autowired
    ViewCaseData repo;
    
    @GetMapping("/add")
    public String showAddTestCaseForm() {
        return "addTestCase"; // Thymeleaf template name for the addTestCase.html file
    }  

    @RequestMapping("/saveCase")
    @ResponseBody
    public String saveCase(ViewCase viewCase) {
        repo.save(viewCase);
        return "Success";
    }
    

}
