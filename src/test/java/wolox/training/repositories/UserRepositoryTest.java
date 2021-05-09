package wolox.training.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        userRepository.save(UserTestHelper.aUserList().get(0));
        userRepository.save(UserTestHelper.aUserList().get(1));
        userRepository.save(UserTestHelper.aUserList().get(2));
        User user = userRepository.findFirstByUsername(TestConstants.USER_MOCK_USERNAME_NAME).orElseThrow(
                null);
        assertNotNull(user);
    }
}
