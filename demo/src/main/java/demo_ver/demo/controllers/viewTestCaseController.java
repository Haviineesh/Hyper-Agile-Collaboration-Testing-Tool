package demo_ver.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class viewTestCaseController {
 
    @GetMapping("/viewTestCase")
    public String viewTestCase() {
        return "viewTestCase";
    }
}
