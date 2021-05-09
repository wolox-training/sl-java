package wolox.training.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
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
        bookRepository.saveAll(BookTestHelper.aBookList());
        Book authorsBook = bookRepository.findFirstByAuthor(TestConstants.BOOK_MOCK_AUTHOR_NAME).orElseThrow(
                null);
        assertNotNull(authorsBook);
    }

    @Test
    public void whenFindAllByPublisherAndGenreAndYear_thenReturnTheBook() {
        Book bookToFind = BookTestHelper.aBook();
        bookRepository.saveAll(BookTestHelper.aBookList());
        List<Book> books =
                bookRepository.findAllByPublisherAndGenreAndYear
                        (bookToFind.getPublisher(), bookToFind.getGenre(), bookToFind.getYear());
        assertEquals(1, books.size());
    }
}
