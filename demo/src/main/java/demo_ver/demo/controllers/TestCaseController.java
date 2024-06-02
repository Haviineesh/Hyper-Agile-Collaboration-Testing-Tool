package demo_ver.demo.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import java.security.Principal; // Import Principal for getting logged-in user's information
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// import org.hyperledger.fabric.gateway.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo_ver.demo.model.ManageUser;
import demo_ver.demo.model.TestCase;
import demo_ver.demo.service.ManageRoleService;
import demo_ver.demo.service.ManageUserService;
import demo_ver.demo.service.ViewCaseService;


@Controller
public class TestCaseController {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private ViewCaseService viewCaseService;
    
    @Autowired
    private RestTemplate restTemplate;

    // @RequestMapping("/getData")
    // public String getDataFromApi() {
    //     String uri = "http://172.20.228.232:3000/getAllTestCases";

    //     try {
    //         String result = restTemplate.getForObject(uri, String.class);

    //         // Assuming response is JSON, parse it into a list of TestCase objects
    //         ObjectMapper objectMapper = new ObjectMapper();
    //         List<TestCase> testCases = objectMapper.readValue(result, List.class);

    //         // Build a formatted string to list data
    //         StringBuilder dataList = new StringBuilder();
    //         for (TestCase testCase : testCases) {
    //             dataList.append("ID: ").append(testCase.getIdtest_cases()).append(", ");
    //             dataList.append("Case Number: ").append(testCase.getProjectId()).append(", ");
    //             dataList.append("Priority: ").append(testCase.getSmartContractID()).append(",\n");
    //         }

    //         return dataList.toString(); // Return a string containing the formatted data

    //     } catch (RestClientResponseException e) {
    //         // Handle specific HTTP error responses
    //         return "Error: " + e.getMessage();
    //     } catch (Exception e) {
    //         // Handle unexpected exceptions
    //         return "Error: " + e.getMessage();
    //     }
    // }
    

   @GetMapping("/view")
    public String viewCase(Model model,Principal principal) throws JsonProcessingException{
    List<TestCase> testCases = ViewCaseService.findAllList();

    // Assuming ManageUserService.getAllUsers() returns a List<ManageUser>
    List<ManageUser> allUsers = ManageUserService.getAllUsers();
    String username = principal.getName();

    // Set username for each test case
    for (TestCase testCase : testCases) {
        List<Integer> userIds = testCase.getUserID();
        List<String> usernames = userIds.stream()
                .map(userId -> {
                    ManageUser user = ManageUserService.getUserById(userId);
                    return (user != null) ? user.getUsername() : "";
                })
                .collect(Collectors.toList());

        // Assuming you want to concatenate usernames into a single string
        testCase.setUsername(String.join(", ", usernames));
    }

    List<TestCase> userTestCases = viewCaseService.findTestCasesByUsername(username);


    model.addAttribute("testCase", userTestCases);
    model.addAttribute("users1", allUsers);
    // model.addAttribute("allTestCases", ViewCaseService.findAllList());
    // model.addAttribute("userTestCases", viewCaseService.findTestCasesByUsername(username));
    return "viewTestCase";
}

    

    @GetMapping("/add")
    public String showAddTestCaseForm(Model model) {
        model.addAttribute("testCase", new TestCase());
        model.addAttribute("users", ManageUserService.getAllUsers());
        return "addTestCase";
    }

    @PostMapping("/save")
    public String addTestCaseForm(TestCase testCase, @RequestParam("userID") List<Integer> userID, Model model) throws JsonProcessingException{
        model.addAttribute("tests", ViewCaseService.findAllList());
        model.addAttribute("users", ManageUserService.getAllUsers()); // I added this so that user list will always show even if got validation errors

         // Check if the test case name already exists
        if (viewCaseService.istestCaseExists(testCase.getTestCaseName())) {
            model.addAttribute("testCaseNameExists", true);
            return "addTestCase";
        }
        // Check if the deadline is later than the date created
        if (!isDeadlineLaterThanDateCreated(testCase.getDateCreated(), testCase.getDeadline())) {
            model.addAttribute("deadlineInvalid", true);
            return "addTestCase";
        }

        // Proceed with adding the test case
        viewCaseService.addTestCaseForm(testCase, userID);
        return "redirect:/view";
    }

    // Helper method to check if deadline is later than date created
    private boolean isDeadlineLaterThanDateCreated(String dateCreated, String deadline) {
        LocalDate createdDate = LocalDate.parse(dateCreated);
        LocalDate deadlineDate = LocalDate.parse(deadline);
        return deadlineDate.isAfter(createdDate);
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
    public String editTestCaseForm(TestCase testCase, @RequestParam("userID") List<Integer> userID, Model model) throws JsonProcessingException{

        model.addAttribute("tests", ViewCaseService.findAllList());
        model.addAttribute("users", ManageUserService.getAllUsers()); // I added this so that user list will always show even if got validation errors
        // if (viewCaseService.istestCaseExists(testCase.getTestCaseName())) {
        //     model.addAttribute("testCaseNameExists", true);
        //     return "EditTestCase";
        // }
        // Check if the deadline is later than the date created
        if (!isDeadlineLaterThanDateCreated(testCase.getDateCreated(), testCase.getDeadline())) {
            model.addAttribute("deadlineInvalid", true);
            return "EditTestCase";
        }
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
