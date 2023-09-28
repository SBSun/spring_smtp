package study.smtp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.smtp.authenticationNumber.AuthenticationService;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final AuthenticationService authenticationService;

    @Transactional
    public void sendEmail(String toEmail){
        SimpleMailMessage emailForm = createEmailForm(toEmail);

        emailSender.send(emailForm);
    }

    private SimpleMailMessage createEmailForm(String toEmail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("작심삼칩 인증번호");
        message.setText(authenticationService.createAuthenticationNumber(toEmail));

        return message;
    }
}
