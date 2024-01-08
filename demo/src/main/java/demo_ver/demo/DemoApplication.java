package demo_ver.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import demo_ver.demo.mail.MailService;
import demo_ver.demo.model.TestCase;


@SpringBootApplication
@RestController
@ComponentScan("demo_ver.demo")

public class DemoApplication {

	public static void main(String[] args) {
		// Create an instance of TestCase
        TestCase testCase = new TestCase();
        // Set other properties of the testCase as needed

        // Create an instance of MailService
        MailService mailService = new MailService();

        // Call the checkDeadlineAndSendNotification method with the mailService instance
        testCase.checkDeadlineAndSendNotification(mailService);
		
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("Let's inspect the beans provided by Spring Boot: RUN LETSGOO!");
	}

}
