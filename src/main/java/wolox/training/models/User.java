package wolox.training.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotOwnedException;

@Entity(name = User.USERS_ENTITY_NAME)
public class User {

    public static final String BOOK_ALREADY_OWNED_BY_MSG = "The book are already owned by the user";
    public static final String BOOK_NOT_OWNED_MSG = "user does not own the book";
    public static final String USERS_ENTITY_NAME = "users";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    private String username;

    @NotNull
    private String name;

    @NotNull
    private LocalDate birthdate;

    @NotNull
    @ManyToMany
    private List<Book> books = new ArrayList<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Add a book if it is not associated
     *
     * @param book: The book to associate with the user (Book)
     */
    public void addBook(Book book) {
        if (books.contains(book)) {
            throw new BookAlreadyOwnedException(BOOK_ALREADY_OWNED_BY_MSG);
        }
        books.add(book);
    }

    /**
     * Remove a book if the user has it associated
     *
     * @param book: The book to be disassociated from the user (Book)
     */

    public void removeBook(Book book) {
        if (!books.remove(book)) {
            throw new BookNotOwnedException(BOOK_NOT_OWNED_MSG);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", books=" + books +
                '}';
    }
}
