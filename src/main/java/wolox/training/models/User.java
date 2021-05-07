package wolox.training.models;

import static com.google.common.base.Preconditions.checkNotNull;
import static wolox.training.utils.MessageError.CHECK_NOT_NULL_MESSAGE;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
import wolox.training.utils.EntityConstants;
import wolox.training.utils.MessageError;

@Entity(name = EntityConstants.USERS_ENTITY_NAME)
@ApiModel(description = "Users from the Training APP")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    private String username;

    @NotNull
    private String name;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    @ApiModelProperty(value = "The format of the birthday must be dd-MM-yyyy", example = "13-03-1989")
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
        this.username = checkNotNull(username, CHECK_NOT_NULL_MESSAGE);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = checkNotNull(name, CHECK_NOT_NULL_MESSAGE);
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = checkNotNull(birthdate, CHECK_NOT_NULL_MESSAGE);
    }

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

    public void setBooks(List<Book> books) {
        this.books = checkNotNull(books, CHECK_NOT_NULL_MESSAGE);
    }

    /**
     * Add a book if it is not associated
     *
     * @param book: The book to associate with the user (Book)
     */
    public void addBook(Book book) {
        if (books.contains(book)) {
            throw new BookAlreadyOwnedException(MessageError.BOOK_ALREADY_OWNED_MSG);
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
            throw new BookNotOwnedException(MessageError.BOOK_NOT_OWNED_MSG);
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
