package study.smtp.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.smtp.service.EmailService;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity sendEmail(@RequestParam String email) throws MessagingException {
        emailService.sendEmail(email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/authentication")
    public ResponseEntity authenticationEmail(@RequestParam String email){
        emailService.authenticationEmail(email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verification")
    public ResponseEntity<Boolean> verificationEmail(@RequestParam String email){
        boolean isVerified = emailService.verificationEmail(email);

        return ResponseEntity.ok(isVerified);
    }
}
