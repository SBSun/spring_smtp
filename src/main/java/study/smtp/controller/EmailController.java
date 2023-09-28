package study.smtp.controller;

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
    public ResponseEntity sendEmail(@RequestParam String email){
        emailService.sendEmail(email);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
