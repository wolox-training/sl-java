package wolox.training.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wolox.training.models.Book;
import wolox.training.test.TestConstants;
import wolox.training.test.util.BookTestHelper;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void whenCreateBook_thenBookIsPersisted() {
        Book persistedBook = bookRepository.save(BookTestHelper.aBook());
        assertNotNull(persistedBook);
    }

    @Test
    public void whenFindFirstByAuthor_thenReturnFirstBookWithAuthorName() {
        bookRepository.save(BookTestHelper.aBookList().get(0));
        bookRepository.save(BookTestHelper.aBookList().get(1));
        bookRepository.save(BookTestHelper.aBookList().get(2));
        Book authorsBook = bookRepository.findFirstByAuthor(TestConstants.BOOK_MOCK_AUTHOR_NAME).orElseThrow(
                null);
        assertNotNull(authorsBook);
    }
}
