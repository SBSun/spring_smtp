package study.smtp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    public void sendEmail(String toEmail){
        SimpleMailMessage emailForm = createEmailForm(toEmail);

        emailSender.send(emailForm);
    }

    private SimpleMailMessage createEmailForm(String toEmail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("작심삼칩 인증번호");
        message.setText(createAuthenticationNumber(toEmail));

        return message;
    }

    private String createAuthenticationNumber(String email){
        Random random = new Random();
        StringBuffer number = new StringBuffer();

        for(int i = 0; i < 6; i++){
            // 10 미만의 랜덤 정수
            int randomNum = random.nextInt(10);
            number.append(randomNum);
        }

        redisService.setValues(email, number.toString(), authCodeExpirationMillis);

        return number.toString();
    }

    @Transactional
    public boolean verificationsAuthenticationNumber(String email, String authenticationNumber){
        String findAuthenticationNumber = redisService.findByKey(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."))
                .toString();

        if(findAuthenticationNumber.equals(authenticationNumber)){
            redisService.deleteByKey(email);
            return  true;
        }else{
            return false;
        }
    }
}
