package wolox.training.test.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import wolox.training.models.User;


public class UserTestHelper {

    private static PasswordEncoder passwordEncoder;

    public static User aUser() {
        User user1 = new User();
        user1.setUsername("stelome");
        user1.setName("andrew");
        user1.setBirthdate(LocalDate.of(1997, 5, 23));
        user1.setPassword("12345678");
        return user1;
    }

    public static List<User> aUserList() {
        User user1 = new User();
        user1.setUsername("esfrrr");
        user1.setName("pedro");
        user1.setBirthdate(LocalDate.of(1997, 5, 23));

        User user2 = new User();
        user2.setUsername("stelome");
        user2.setName("andrew");
        user2.setBirthdate(LocalDate.of(1997, 5, 23));

        User user3 = new User();
        user3.setUsername("adessse");
        user3.setName("anibal");
        user3.setBirthdate(LocalDate.of(1997, 5, 23));

        return Arrays.asList(user1, user2, user3);
    }
}
