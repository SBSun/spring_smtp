package study.smtp.authenticationNumber;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationNumberRepository authenticationNumberRepository;

    public String createAuthenticationNumber(String email){
        Random random = new Random();
        StringBuffer number = new StringBuffer();

        for(int i = 0; i < 6; i++){
            // 10 미만의 랜덤 정수
            int randomNum = random.nextInt(10);
            number.append(randomNum);
        }

        AuthenticationNumber authenticationNumber = new AuthenticationNumber(email, number.toString());
        authenticationNumberRepository.save(authenticationNumber);

        return number.toString();
    }

    @Transactional
    public boolean verificationsAuthenticationNumber(String email, String authenticationNumber){
        AuthenticationNumber findAuthenticationNumber = authenticationNumberRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if(findAuthenticationNumber.getAuthenticationNumber().equals(authenticationNumber)){
            authenticationNumberRepository.deleteById(email);
            return true;
        }else{
            return false;
        }
    }
}
