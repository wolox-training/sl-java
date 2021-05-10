package wolox.training.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wolox.training.models.User;
import wolox.training.test.TestConstants;
import wolox.training.test.util.UserTestHelper;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenCreateUser_thenUserIsPersisted() {
        User persistedUser = userRepository.save(UserTestHelper.aUser());
        assertNotNull(persistedUser);
    }

    @Test
    public void whenFindFirstByUsername_thenReturnFirstUserWithUsername() {
        userRepository.saveAll(UserTestHelper.aUserList());
        User user = userRepository.findFirstByUsername(TestConstants.USER_MOCK_USERNAME_NAME).orElseThrow(
                null);
        assertNotNull(user);
    }

    @Test
    public void WhenFindAllByBirthdateBetweenAndNameIgnoreCaseContaining_thenReturnUser() {
        userRepository.saveAll(UserTestHelper.aUserList());
        List<User> users = userRepository.findAllByBirthdateBetweenAndNameIgnoreCaseContaining(
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2020, 12, 31),
                "REW"
        );
        assertEquals(1, users.size());
    }

    @Test
    public void WhenFindAllByBirthdateBetweenAndNameIgnoreCaseContainingAndDatesAreNull_thenReturnUser() {
        userRepository.saveAll(UserTestHelper.aUserList());
        List<User> users = userRepository.findAllByBirthdateBetweenAndNameIgnoreCaseContaining(
                null,
                null,
                "REW"
        );
        assertEquals(1, users.size());
    }
}
