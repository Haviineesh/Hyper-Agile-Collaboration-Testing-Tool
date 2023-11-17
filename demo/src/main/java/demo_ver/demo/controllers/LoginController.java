package demo_ver.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {

    @GetMapping("/")
    public String login() {
        return "login";
    }

//    @GetMapping("/change-password")
  //  public String ChangePassword() {
    //    return "change-password";
    //}
}
