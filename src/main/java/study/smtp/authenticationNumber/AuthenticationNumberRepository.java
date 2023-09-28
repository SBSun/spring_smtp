package study.smtp.authenticationNumber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationNumberRepository extends JpaRepository<AuthenticationNumber, String> {
}
