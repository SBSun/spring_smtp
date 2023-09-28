package study.smtp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendEmail(String toEmail){
        SimpleMailMessage emailForm = createEmailForm(toEmail);

        emailSender.send(emailForm);
    }

    private SimpleMailMessage createEmailForm(String toEmail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("작심삼칩 인증번호");
        message.setText(createAuthenticationNumber());

        return message;
    }

    public String createAuthenticationNumber(){
        Random random = new Random();
        StringBuffer number = new StringBuffer();

        for(int i = 0; i < 6; i++){
            // 10 미만의 랜덤 정수
            int randomNum = random.nextInt(10);
            number.append(randomNum);
        }

        return number.toString();
    }
}
