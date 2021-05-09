package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT b FROM Book b "
            + "WHERE (:publisher IS NULL OR b.publisher = :publisher) "
            + "AND (:year IS NULL OR b.year = :year) "
            + "AND (:genre IS NULL OR b.genre = :genre)")
    List<Book> findAllByPublisherAndGenreAndYear(@Param("publisher") String publisher,
            @Param("genre") String genre,
            @Param("year") String year);
}

