package study.smtp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    private final JavaMailSender emailSender;
    private final RedisService redisService;

    @Transactional
    public void sendEmail(String toEmail) throws MessagingException {
        MimeMessage emailForm = createEmailForm(toEmail);

        emailSender.send(emailForm);
    }

    private MimeMessage createEmailForm(String toEmail) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject("작심삼칩 인증 메일");

        String body = "";
        body += "<h1> 안녕하세요. 작심삼칩입니다.</h1>";
        body += "<br>";
        body += "<p>아래 링크를 클릭하면 이메일 인증이 완료됩니다.<p>";
        body += "<a href='http://localhost:8080/emails/authentication?email=" + toEmail + "'>인증 링크</a>";
        
        message.setText(body, "utf-8", "html");

        redisService.setValues(toEmail, "false", authCodeExpirationMillis);

        return message;
    }

    @Transactional
    public void authenticationEmail(String email){
        if(redisService.findByKey(email).isPresent()){
            redisService.deleteByKey(email);
        }

        redisService.setValues(email, "true", authCodeExpirationMillis);
    }

    @Transactional
    public boolean verificationEmail(String email){
        if(redisService.findByKey(email).isPresent()){
            redisService.deleteByKey(email);
            return true;
        }
        else
            return false;
    }
}
