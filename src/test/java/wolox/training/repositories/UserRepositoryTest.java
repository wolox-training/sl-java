package wolox.training.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wolox.training.models.Professor;
import wolox.training.models.Student;
import wolox.training.models.User;
import wolox.training.test.TestConstants;
import wolox.training.test.util.UserTestHelper;
import wolox.training.utils.EntityConstants;

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
        Page<User> users = userRepository.findAllByBirthdateBetweenAndNameIgnoreCaseContaining(
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2020, 12, 31),
                "REW",
                PageRequest.of(TestConstants.DEFAULT_PAGE_NUMBER, TestConstants.DEFAULT_PAGE_SIZE, Sort.unsorted()
                ));
        assertEquals(1, users.getTotalElements());
    }

    @Test
    public void WhenFindAllByBirthdateBetweenAndNameIgnoreCaseContainingAndDatesAreNull_thenReturnUser() {
        userRepository.saveAll(UserTestHelper.aUserList());
        Page<User> users = userRepository.findAllByBirthdateBetweenAndNameIgnoreCaseContaining(
                null,
                null,
                "REW",
                PageRequest.of(TestConstants.DEFAULT_PAGE_NUMBER, TestConstants.DEFAULT_PAGE_SIZE, Sort.unsorted()
                ));
        assertEquals(1, users.getTotalElements());
    }

    @Test
    public void whenCreateUserStudent_thenUserIsPersisted() {
        Student student = UserTestHelper.aStudent();
        User student1 = userRepository.save(student);
        Professor professor = UserTestHelper.aProfessor();
        User professor1 = userRepository.save(professor);
        List<User> users = userRepository.findAll();
        assertNotNull(users);
        assertEquals(EntityConstants.STUDENT_DISCRIMINATOR_VALUE, student1.getUserType());
        assertEquals(EntityConstants.PROFESSOR_DISCRIMINATOR_VALUE, professor1.getUserType());
        assertEquals("2012", ((Student) student1).getYear());
        assertEquals("Math", ((Professor) professor1).getSubject());
    }
}
