package study.smtp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void createAuthenticationNumber(){
        String authenticationNumber = emailService.createAuthenticationNumber();

        System.out.println(authenticationNumber);
    }
}