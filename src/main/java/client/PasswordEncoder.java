import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoder {

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(20);
    }

}