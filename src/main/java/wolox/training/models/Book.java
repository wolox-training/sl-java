package wolox.training.models;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static wolox.training.utils.MessageError.CHECK_ARGUMENT_EMPTY_MESSAGE;
import static wolox.training.utils.MessageError.CHECK_NOT_NULL_MESSAGE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_SEQ")
    @SequenceGenerator(name = "BOOK_SEQ", sequenceName = "BOOK_SEQ")
    @Setter(AccessLevel.NONE)
    private long id;

    private String genre;

    @NotNull
    @Setter(AccessLevel.NONE)
    private String author;

    @NotNull
    @Setter(AccessLevel.NONE)
    private String image;

    @NotNull
    @Setter(AccessLevel.NONE)
    private String title;

    @NotNull
    @Setter(AccessLevel.NONE)
    private String subtitle;

    @NotNull
    @Setter(AccessLevel.NONE)
    private String publisher;

    @NotNull
    @Setter(AccessLevel.NONE)
    private String year;

    @NotNull
    @Setter(AccessLevel.NONE)
    private int pages;

    @NotNull
    @Setter(AccessLevel.NONE)
    private String isbn;

    public void setAuthor(String author) {
        checkNotNull(author, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(author), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.author = author;
    }

    public void setImage(String image) {
        checkNotNull(image, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(image), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.image = image;
    }

    public void setTitle(String title) {
        checkNotNull(title, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(title), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        checkNotNull(subtitle, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(subtitle), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.subtitle = subtitle;
    }

    public void setPublisher(String publisher) {
        checkNotNull(publisher, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(publisher), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.publisher = publisher;
    }

    public void setYear(String year) {
        checkNotNull(year, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(year), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.year = year;
    }

    public void setPages(int pages) {
        this.pages = checkNotNull(pages, CHECK_NOT_NULL_MESSAGE);
    }

    public void setIsbn(String isbn) {
        checkNotNull(isbn, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(isbn), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.isbn = isbn;
    }
}
