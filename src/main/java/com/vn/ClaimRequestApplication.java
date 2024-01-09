package com.vn;

import com.vn.dto.view.ClaimEmailDTO;
import com.vn.utils.email.EmailService;
import com.vn.utils.email.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

@SpringBootApplication
@EnableAsync
public class ClaimRequestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClaimRequestApplication.class, args);
	}
}


