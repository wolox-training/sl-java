package wolox.training.models;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static wolox.training.utils.MessageError.CHECK_ARGUMENT_EMPTY_MESSAGE;
import static wolox.training.utils.MessageError.CHECK_BIRTHDAY_BEFORE_NOW;
import static wolox.training.utils.MessageError.CHECK_NOT_NULL_MESSAGE;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.util.ObjectUtils;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotOwnedException;
import wolox.training.utils.EntityConstants;
import wolox.training.utils.MessageError;

@Data
@Entity(name = EntityConstants.USERS_ENTITY_NAME)
@ApiModel(description = "Users from the Training APP")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ")
    @Setter(AccessLevel.NONE)
    private long id;

    @NotNull
    private String username;

    @NotNull
    private String name;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @ApiModelProperty(value = "The format of the birthday must be dd-MM-yyyy", example = "13-03-1989")
    private LocalDate birthdate;

    @NotNull
    @ManyToMany
    private List<Book> books = new ArrayList<>();

    private String password;

    public void setUsername(String username) {
        checkNotNull(username, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(username), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.username = username;
    }

    public void setName(String name) {
        checkNotNull(name, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(name), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.name = name;
    }

    public void setBirthdate(LocalDate birthdate) {
        checkArgument(birthdate.isBefore(LocalDate.now()), CHECK_BIRTHDAY_BEFORE_NOW);
        this.birthdate = checkNotNull(birthdate, CHECK_NOT_NULL_MESSAGE);
    }

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

    public void setBooks(List<Book> books) {
        checkNotNull(books, CHECK_NOT_NULL_MESSAGE);
        this.books = books;
    }

    public void setPassword(String password) {
        checkNotNull(password, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(password), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.password = password;
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
}
