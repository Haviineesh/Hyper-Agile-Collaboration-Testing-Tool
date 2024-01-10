package demo_ver.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.scheduling.annotation.EnableScheduling;

import demo_ver.demo.mail.MailService;
import demo_ver.demo.model.TestCase;


@SpringBootApplication
@RestController
@ComponentScan("demo_ver.demo")

public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("Let's inspect the beans provided by Spring Boot: RUN LETSGOO!");
	}

}
