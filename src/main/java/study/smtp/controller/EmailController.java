package study.smtp.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
    public ModelAndView authenticationEmail(@RequestParam String email, HttpServletResponse response){
        emailService.authenticationEmail(email);

        return new ModelAndView("success");
    }

    @GetMapping("/verification")
    public ResponseEntity<Boolean> verificationEmail(@RequestParam String email){
        boolean isVerified = emailService.verificationEmail(email);

        return ResponseEntity.ok(isVerified);
    }
}
