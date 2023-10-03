package study.smtp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisService redisService;

    @Transactional
    public void sendEmail(String toEmail) throws MessagingException {
        MimeMessage emailForm = createEmailForm(toEmail);

        emailSender.send(emailForm);
    }

    private MimeMessage createEmailForm(String toEmail) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("작심삼칩 인증 메일");

        // 템플릿에 전달할 데이터 설정
        Context context = new Context();
        context.setVariable("email", toEmail);

        // 메일 내용 설정 : 템플릿 프로세스
        String html = templateEngine.process("email", context);
        helper.setText(html, true);

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
