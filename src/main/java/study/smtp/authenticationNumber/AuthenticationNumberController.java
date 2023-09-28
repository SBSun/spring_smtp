package study.smtp.authenticationNumber;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authtication-number")
@RequiredArgsConstructor
public class AuthenticationNumberController {

    private final AuthenticationService authenticationService;

    @GetMapping("/verifications")
    public ResponseEntity<Boolean> verificationsEmail(@RequestParam String email, @RequestParam String authenticationNumber){
        boolean isVerified = authenticationService.verificationsAuthenticationNumber(email, authenticationNumber);

        return ResponseEntity.ok(isVerified);
    }
}
