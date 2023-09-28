package study.smtp.authenticationNumber;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AuthenticationNumber {

    @Id
    private String email;

    private String authenticationNumber;

    public AuthenticationNumber(String email, String authenticationNumber) {
        this.email = email;
        this.authenticationNumber = authenticationNumber;
    }
}
