package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    /**
     * Find a book by its author's name
     *
     * @param author: Name of the author of the book to find (String)
     *
     * @return First book with the author's name
     */
    Optional<Book> findFirstByAuthor(String author);

    Optional<Book> findFirstByIsbn(String isbn);
}

